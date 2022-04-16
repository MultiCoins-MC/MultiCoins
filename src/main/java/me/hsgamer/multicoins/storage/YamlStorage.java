package me.hsgamer.multicoins.storage;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.config.AutoSaveConfig;
import me.hsgamer.multicoins.object.CoinEntry;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.multicoins.object.CoinStorage;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class YamlStorage implements CoinStorage {
    private static final MultiCoins instance;
    private static final File baseFolder;
    private static final Map<String, AutoSaveConfig> configs = new HashMap<>();

    static {
        instance = JavaPlugin.getPlugin(MultiCoins.class);
        baseFolder = new File(instance.getDataFolder(), "coins");
    }

    private AutoSaveConfig getConfig(String name) {
        return configs.computeIfAbsent(name, s -> {
            AutoSaveConfig config = new AutoSaveConfig(instance, new BukkitConfig(new File(baseFolder, s + ".yml")));
            config.setup();
            return config;
        });
    }

    private void removeConfig(String name) {
        AutoSaveConfig config = configs.remove(name);
        if (config != null) {
            config.finalSave();
        }
    }

    @Override
    public CompletableFuture<Map<UUID, Double>> load(CoinHolder holder) {
        Config config = getConfig(holder.getName());
        Map<String, Object> values = config.getValues(false);
        return CompletableFuture.supplyAsync(() -> {
            Map<UUID, Double> map = new HashMap<>();
            values.forEach((uuid, value) -> map.put(UUID.fromString(uuid), Double.parseDouble(String.valueOf(value))));
            return map;
        });
    }

    @Override
    public CompletableFuture<Void> save(CoinEntry topEntry, boolean onUnregister) {
        Config config = getConfig(topEntry.getHolder().getName());
        CompletableFuture<Void> future = new CompletableFuture<>();
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                config.set(topEntry.getUuid().toString(), topEntry.getBalance());
                future.complete(null);
            }
        };
        if (onUnregister) {
            runnable.run();
        } else {
            runnable.runTask(instance);
        }
        return future;
    }

    @Override
    public void onUnregister(CoinHolder holder) {
        removeConfig(holder.getName());
    }
}

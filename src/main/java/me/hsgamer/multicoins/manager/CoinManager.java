package me.hsgamer.multicoins.manager;

import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.builder.CoinStorageBuilder;
import me.hsgamer.multicoins.config.MainConfig;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.multicoins.object.CoinStorage;
import me.hsgamer.multicoins.storage.YamlStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CoinManager {
    private final Map<String, CoinHolder> holders = new HashMap<>();
    private final Map<String, CoinFormatter> formatters = new HashMap<>();
    private final CoinFormatter defaultFormatter = new CoinFormatter();
    private final MultiCoins instance;

    public CoinManager(MultiCoins instance) {
        this.instance = instance;
    }

    public void setup() {
        MainConfig.COINS.getValue().forEach(name -> {
            CoinStorage storage = CoinStorageBuilder.INSTANCE.build(MainConfig.STORAGE_TYPE.getValue(), instance).orElseGet(YamlStorage::new);
            CoinHolder holder = new CoinHolder(instance, name, storage);
            holder.register();
            holders.put(name, holder);
        });
        CoinFormatter.setNullValueSupplier(() -> "0");
        defaultFormatter.addReplacer("name", (uuid, bigDecimal) -> Optional.ofNullable(uuid).map(Bukkit::getOfflinePlayer).map(OfflinePlayer::getName).orElse(""));
        formatters.putAll(MainConfig.FORMATTERS.getValue());
        formatters.values().forEach(topFormatter -> topFormatter.addReplacer("name", (uuid, bigDecimal) -> Optional.ofNullable(uuid).map(Bukkit::getOfflinePlayer).map(OfflinePlayer::getName).orElse("")));
    }

    public void disable() {
        holders.values().forEach(CoinHolder::unregister);
        holders.clear();
        formatters.clear();
    }

    public void create(UUID uuid) {
        holders.values().forEach(holder -> holder.getOrCreateEntry(uuid));
    }

    public Optional<CoinHolder> getHolder(String name) {
        return Optional.ofNullable(holders.get(name));
    }

    public CoinFormatter getFormatter(String name) {
        return formatters.getOrDefault(name, defaultFormatter);
    }
}

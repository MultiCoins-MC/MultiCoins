package me.hsgamer.multicoins.object;

import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.config.MainConfig;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

public final class CoinHolder {
    private final MultiCoins instance;
    private final String name;
    private final double startBalance;
    private final boolean allowNegative;
    private final CoinStorage storage;
    private final Map<UUID, CoinEntry> entryMap = new ConcurrentHashMap<>();
    private final Queue<UUID> saveQueue = new ConcurrentLinkedQueue<>();
    private BukkitTask saveTask;

    public CoinHolder(MultiCoins instance, String name, CoinStorage storage) {
        this.instance = instance;
        this.name = name;
        this.storage = storage;
        this.startBalance = MainConfig.START_BALANCES.getValue().getOrDefault(name, 0.0);
        this.allowNegative = MainConfig.NEGATIVE_ALLOWED_COINS.getValue().contains(name);
    }

    private void onCreateEntry(CoinEntry entry) {
        saveQueue.add(entry.getUuid());
    }

    private void onRemoveEntry(CoinEntry entry) {
        entryMap.remove(entry.getUuid());
    }

    public CompletableFuture<Void> save(CoinEntry entry, boolean onUnregister) {
        return storage.save(entry, onUnregister);
    }

    public void register() {
        storage.onRegister(this);
        storage.load(this)
                .whenComplete((entries, throwable) -> {
                    if (throwable != null) {
                        instance.getLogger().log(Level.SEVERE, "Failed to load top entries", throwable);
                    }
                    if (entries != null) {
                        entries.forEach((uuid, value) -> getOrCreateEntry(uuid).setBalance(value, false));
                    }
                });

        int saveDelay = MainConfig.SAVE_DELAY.getValue();
        saveTask = instance.getServer().getScheduler().runTaskTimerAsynchronously(instance, () -> {
            List<UUID> list = new ArrayList<>();
            for (int i = 0; i < MainConfig.SAVE_ENTRY_PER_TICK.getValue(); i++) {
                UUID uuid = saveQueue.poll();
                if (uuid == null) {
                    break;
                }
                CoinEntry entry = getOrCreateEntry(uuid);
                entry.save();
                list.add(uuid);
            }
            if (!list.isEmpty()) {
                saveQueue.addAll(list);
            }
        }, saveDelay, saveDelay);
    }

    public void unregister() {
        if (saveTask != null) {
            saveTask.cancel();
        }
        entryMap.values().forEach(entry -> {
            entry.save(true);
            onRemoveEntry(entry);
        });
        storage.onUnregister(this);
        entryMap.clear();
    }

    public CoinEntry getOrCreateEntry(UUID uuid) {
        return entryMap.computeIfAbsent(uuid, u -> {
            CoinEntry entry = new CoinEntry(uuid, this, startBalance);
            onCreateEntry(entry);
            return entry;
        });
    }

    public String getName() {
        return name;
    }

    public boolean isAllowNegative() {
        return allowNegative;
    }
}

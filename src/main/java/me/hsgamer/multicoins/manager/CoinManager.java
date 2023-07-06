package me.hsgamer.multicoins.manager;

import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.topper.core.holder.DataHolder;
import me.hsgamer.topper.core.storage.DataStorage;

import java.util.*;
import java.util.function.Function;

public class CoinManager {
    private final Map<String, CoinHolder> holders = new HashMap<>();
    private final MultiCoins instance;
    private Function<DataHolder<Double>, DataStorage<Double>> storageSupplier;

    public CoinManager(MultiCoins instance) {
        this.instance = instance;
    }

    public void setup() {
        storageSupplier = instance.getNumberStorageBuilder().buildSupplier(instance.getMainConfig().getStorageType());
        instance.getMainConfig().getFormatters().forEach((name, formatter) -> {
            CoinHolder holder = new CoinHolder(instance, name, formatter);
            holder.register();
            holders.put(name, holder);
        });
    }

    public Function<DataHolder<Double>, DataStorage<Double>> getStorageSupplier() {
        return storageSupplier;
    }

    public void disable() {
        holders.values().forEach(CoinHolder::unregister);
        holders.clear();
    }

    public void create(UUID uuid) {
        holders.values().forEach(holder -> {
            if (holder.getEntry(uuid).isPresent()) {
                holder.loadIfExist(uuid);
            } else {
                holder.getOrCreateEntry(uuid);
            }
        });
    }

    public void remove(UUID uuid) {
        holders.values().forEach(holder -> holder.removeEntry(uuid));
    }

    public Optional<CoinHolder> getHolder(String name) {
        return Optional.ofNullable(holders.get(name));
    }

    public List<String> getHolders() {
        return new ArrayList<>(holders.keySet());
    }
}

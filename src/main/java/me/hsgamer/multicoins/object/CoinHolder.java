package me.hsgamer.multicoins.object;

import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.topper.core.agent.storage.StorageAgent;
import me.hsgamer.topper.core.entry.DataEntry;
import me.hsgamer.topper.core.holder.DataWithAgentHolder;

import java.util.UUID;

public final class CoinHolder extends DataWithAgentHolder<Double> {
    private final StorageAgent<Double> storageAgent;
    private final CoinFormatter coinFormatter;

    public CoinHolder(MultiCoins instance, String name, CoinFormatter coinFormatter) {
        super(name);
        this.coinFormatter = coinFormatter;

        storageAgent = new StorageAgent<>(instance.getLogger(), instance.getCoinManager().getStorageSupplier().apply(this));
        storageAgent.setMaxEntryPerCall(instance.getMainConfig().getStorageSaveEntryPerTick());
        storageAgent.setRunTaskFunction(runnable -> {
            int saveDelay = instance.getMainConfig().getStorageSaveDelay();
            return Scheduler.CURRENT.runTaskTimer(instance, runnable, saveDelay, saveDelay, true)::cancel;
        });
        storageAgent.setLoadOnCreate(true);
        storageAgent.setUrgentLoad(instance.getMainConfig().isStorageUrgentLoad());
        addAgent(storageAgent);
    }

    public CoinFormatter getCoinFormatter() {
        return coinFormatter;
    }

    @Override
    public Double getDefaultValue() {
        return getCoinFormatter().getStartBalance();
    }

    public void loadIfExist(UUID uuid) {
        storageAgent.loadIfExist(uuid);
    }

    public double getBalance(UUID uuid) {
        return getOrCreateEntry(uuid).getValue();
    }

    public boolean setBalance(UUID uuid, double balance) {
        return setBalance(uuid, balance, true);
    }

    public boolean setBalance(UUID uuid, double balance, boolean save) {
        if (balance < 0 && !coinFormatter.isNegativeAllowed()) return false;
        DataEntry<Double> entry = getOrCreateEntry(uuid);
        double oldBalance = entry.getValue();
        if (oldBalance == balance) return true;
        entry.setValue(balance, save);
        return true;
    }

    public boolean giveBalance(UUID uuid, double amount) {
        return setBalance(uuid, getBalance(uuid) + amount);
    }

    public boolean takeBalance(UUID uuid, double amount) {
        return setBalance(uuid, getBalance(uuid) - amount);
    }
}

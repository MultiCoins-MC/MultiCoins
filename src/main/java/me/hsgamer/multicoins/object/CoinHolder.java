package me.hsgamer.multicoins.object;

import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.config.MainConfig;
import me.hsgamer.topper.core.agent.storage.StorageAgent;
import me.hsgamer.topper.core.entry.DataEntry;
import me.hsgamer.topper.core.holder.DataWithAgentHolder;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public final class CoinHolder extends DataWithAgentHolder<Double> {
    private final double startBalance;
    private final boolean allowNegative;
    private final StorageAgent<Double, BukkitTask> storageAgent;

    public CoinHolder(MultiCoins instance, String name) {
        super(name);
        this.startBalance = MainConfig.START_BALANCES.getValue().getOrDefault(name, 0.0);
        this.allowNegative = MainConfig.NEGATIVE_ALLOWED_COINS.getValue().contains(name);

        storageAgent = new StorageAgent<>(instance.getCoinManager().getStorageSupplier().apply(this));
        storageAgent.setMaxEntryPerCall(MainConfig.SAVE_ENTRY_PER_TICK.getValue());
        storageAgent.setRunTaskFunction(runnable -> {
            int saveDelay = MainConfig.SAVE_DELAY.getValue();
            return instance.getServer().getScheduler().runTaskTimerAsynchronously(instance, runnable, saveDelay, saveDelay);
        });
        storageAgent.setCancelTaskConsumer(BukkitTask::cancel);
        storageAgent.setLoadOnCreate(true);
        storageAgent.setUrgentLoad(MainConfig.STORAGE_URGENT_LOAD.getValue());
        addAgent(storageAgent);
    }

    @Override
    public Double getDefaultValue() {
        return startBalance;
    }

    public void loadIfExist(UUID uuid) {
        storageAgent.loadIfExist(uuid);
    }

    public boolean isAllowNegative() {
        return allowNegative;
    }

    public double getBalance(UUID uuid) {
        return getOrCreateEntry(uuid).getValue();
    }

    public boolean setBalance(UUID uuid, double balance) {
        return setBalance(uuid, balance, true);
    }

    public boolean setBalance(UUID uuid, double balance, boolean save) {
        if (balance < 0 && !isAllowNegative()) return false;
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

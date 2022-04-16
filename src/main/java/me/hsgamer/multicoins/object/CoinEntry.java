package me.hsgamer.multicoins.object;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CoinEntry {
    private final UUID uuid;
    private final CoinHolder holder;
    private final AtomicReference<Double> balance = new AtomicReference<>(0.0);
    private final AtomicBoolean isSaving = new AtomicBoolean(false);
    private final AtomicBoolean needSaving = new AtomicBoolean(false);

    public CoinEntry(UUID uuid, CoinHolder holder) {
        this.uuid = uuid;
        this.holder = holder;
    }

    public CoinEntry(UUID uuid, CoinHolder holder, double balance) {
        this(uuid, holder);
        this.balance.set(balance);
    }

    public UUID getUuid() {
        return uuid;
    }

    public CoinHolder getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance.get();
    }

    public boolean setBalance(double balance) {
        return setBalance(balance, true);
    }

    public boolean setBalance(double balance, boolean save) {
        if (balance < 0 && !holder.isAllowNegative()) return false;
        double oldBalance = this.balance.get();
        if (oldBalance == balance) return true;
        this.balance.set(balance);
        if (save) {
            this.needSaving.set(true);
        }
        return true;
    }

    public boolean giveBalance(double amount) {
        return setBalance(getBalance() + amount);
    }

    public boolean takeBalance(double amount) {
        return setBalance(getBalance() - amount);
    }

    public void save(boolean onUnregister) {
        if (isSaving.get()) return;
        if (!needSaving.get()) return;
        needSaving.set(false);
        isSaving.set(true);
        holder.save(this, onUnregister).whenComplete((result, throwable) -> isSaving.set(false));
    }

    public void save() {
        save(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoinEntry coinEntry = (CoinEntry) o;
        return uuid.equals(coinEntry.uuid) && holder.equals(coinEntry.holder) && balance.equals(coinEntry.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, holder, balance);
    }
}

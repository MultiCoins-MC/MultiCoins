package me.hsgamer.multicoins.object;

import me.hsgamer.hscore.bukkit.utils.ColorUtils;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.topper.spigot.number.NumberFormatter;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CoinFormatter extends NumberFormatter {
    private String currencySingular = "$";
    private String currencyPlural = "$";
    private boolean negativeAllowed = false;
    private double startBalance = 0;

    public CoinFormatter(Map<String, Object> map) {
        super(map);
        Optional.ofNullable(map.get("currency-singular")).ifPresent(s -> currencySingular = String.valueOf(s));
        Optional.ofNullable(map.get("currency-plural")).ifPresent(s -> currencyPlural = String.valueOf(s));
        Optional.ofNullable(map.get("negative-allowed")).ifPresent(s -> negativeAllowed = Boolean.parseBoolean(String.valueOf(s)));
        Optional.ofNullable(map.get("start-balance")).map(Objects::toString).flatMap(Validate::getNumber).map(Number::doubleValue).ifPresent(d -> startBalance = d);
    }

    public CoinFormatter() {
        // EMPTY
    }

    public String getCurrencySingular() {
        return currencySingular;
    }

    public void setCurrencySingular(String currencySingular) {
        this.currencySingular = currencySingular;
    }

    public String getCurrencyPlural() {
        return currencyPlural;
    }

    public void setCurrencyPlural(String currencyPlural) {
        this.currencyPlural = currencyPlural;
    }

    public String getCurrency(Number value) {
        return ColorUtils.colorize(value == null || value.intValue() == 1 ? currencySingular : currencyPlural);
    }

    public boolean isNegativeAllowed() {
        return negativeAllowed;
    }

    public void setNegativeAllowed(boolean negativeAllowed) {
        this.negativeAllowed = negativeAllowed;
    }

    public double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }

    @Override
    public String replace(String text, UUID uuid, Number value) {
        return super.replace(text, uuid, value).replace("{currency}", getCurrency(value));
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        map.put("currency-singular", currencySingular);
        map.put("currency-plural", currencyPlural);
        map.put("negative-allowed", negativeAllowed);
        map.put("start-balance", startBalance);
        return map;
    }
}

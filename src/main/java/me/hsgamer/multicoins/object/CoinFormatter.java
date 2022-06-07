package me.hsgamer.multicoins.object;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topper.spigot.formatter.DataFormatter;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CoinFormatter extends DataFormatter {
    private String currencySingular = "$";
    private String currencyPlural = "$";

    public CoinFormatter(Map<String, Object> map) {
        super(map);
        Optional.ofNullable(map.get("currency-singular")).ifPresent(s -> currencySingular = String.valueOf(s));
        Optional.ofNullable(map.get("currency-plural")).ifPresent(s -> currencyPlural = String.valueOf(s));
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

    public String getCurrency(Double value) {
        return MessageUtils.colorize(value == null || value == 1 ? currencySingular : currencyPlural);
    }

    @Override
    public String replace(String text, UUID uuid, Double value) {
        return super.replace(text, uuid, value).replace("{currency}", getCurrency(value));
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = super.toMap();
        map.put("currency-singular", currencySingular);
        map.put("currency-plural", currencyPlural);
        return map;
    }
}

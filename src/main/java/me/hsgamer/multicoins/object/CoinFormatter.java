package me.hsgamer.multicoins.object;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class CoinFormatter {
    private static Supplier<String> nullValueSupplier = () -> "";
    private static Supplier<String> nullUuidSupplier = () -> "";
    private final Map<String, BiFunction<UUID, Double, String>> replacers = new HashMap<>();
    private String displayName = "";
    private String currencySingular = "$";
    private String currencyPlural = "$";
    private int fractionDigits = -1;
    private char decimalSeparator = '.';
    private char groupSeparator = ',';
    private boolean showGroupSeparator = false;
    private DecimalFormat format;

    public CoinFormatter(Map<String, Object> map) {
        Optional.ofNullable(map.get("display-name")).ifPresent(s -> displayName = String.valueOf(s));
        Optional.ofNullable(map.get("currency-singular")).ifPresent(s -> currencySingular = String.valueOf(s));
        Optional.ofNullable(map.get("currency-plural")).ifPresent(s -> currencyPlural = String.valueOf(s));
        Optional.ofNullable(map.get("fraction-digits")).ifPresent(s -> fractionDigits = Integer.parseInt(String.valueOf(s)));
        Optional.ofNullable(map.get("decimal-separator")).ifPresent(s -> decimalSeparator = String.valueOf(s).charAt(0));
        Optional.ofNullable(map.get("group-separator")).ifPresent(s -> groupSeparator = String.valueOf(s).charAt(0));
        Optional.ofNullable(map.get("show-group-separator")).ifPresent(s -> showGroupSeparator = Boolean.parseBoolean(String.valueOf(s)));
    }

    public CoinFormatter() {
        // EMPTY
    }

    public static void setNullValueSupplier(Supplier<String> supplier) {
        nullValueSupplier = supplier;
    }

    public static void setNullUuidSupplier(Supplier<String> supplier) {
        nullUuidSupplier = supplier;
    }

    public void addReplacer(String key, BiFunction<UUID, Double, String> replacer) {
        replacers.put(key, replacer);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public int getFractionDigits() {
        return fractionDigits;
    }

    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public char getGroupSeparator() {
        return groupSeparator;
    }

    public void setGroupSeparator(char groupSeparator) {
        this.groupSeparator = groupSeparator;
    }

    public boolean isShowGroupSeparator() {
        return showGroupSeparator;
    }

    public void setShowGroupSeparator(boolean showGroupSeparator) {
        this.showGroupSeparator = showGroupSeparator;
    }

    private DecimalFormat getFormat() {
        if (format == null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(decimalSeparator);
            symbols.setGroupingSeparator(groupSeparator);

            format = new DecimalFormat();
            format.setRoundingMode(RoundingMode.HALF_EVEN);
            format.setGroupingUsed(showGroupSeparator);
            format.setMinimumFractionDigits(0);
            if (fractionDigits >= 0) {
                format.setMaximumFractionDigits(fractionDigits);
            }
            format.setDecimalFormatSymbols(symbols);
        }
        return format;
    }

    public String format(double value) {
        return getFormat().format(value);
    }

    public String replace(String text, UUID uuid, Double value) {
        String replaced = text
                .replace("{uuid}", uuid != null ? uuid.toString() : nullUuidSupplier.get())
                .replace("{value}", value != null ? format(value) : nullValueSupplier.get())
                .replace("{value_raw}", value != null ? String.valueOf(value) : nullValueSupplier.get())
                .replace("{display_name}", MessageUtils.colorize(displayName))
                .replace("{currency}", MessageUtils.colorize(value == null || value == 1 ? currencySingular : currencyPlural));
        for (Map.Entry<String, BiFunction<UUID, Double, String>> entry : replacers.entrySet()) {
            replaced = replaced.replace("{" + entry.getKey() + "}", entry.getValue().apply(uuid, value));
        }
        return replaced;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("display-name", displayName);
        map.put("currency-singular", currencySingular);
        map.put("currency-plural", currencyPlural);
        map.put("fraction-digits", fractionDigits);
        map.put("decimal-separator", decimalSeparator);
        map.put("group-separator", groupSeparator);
        map.put("show-group-separator", showGroupSeparator);
        return map;
    }
}

package me.hsgamer.multicoins.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.multicoins.object.CoinFormatter;

import java.util.HashMap;
import java.util.Map;

public interface MainConfig {
    @ConfigPath("formatters")
    default Map<String, CoinFormatter> getFormatters() {
        CoinFormatter pointFormatter = new CoinFormatter();
        pointFormatter.setCurrencyPlural("pts");
        pointFormatter.setCurrencySingular("pt");
        pointFormatter.setFractionDigits(0);
        pointFormatter.setDisplayName("Point");
        pointFormatter.setShowGroupSeparator(false);
        pointFormatter.setStartBalance(10);

        CoinFormatter coinFormatter = new CoinFormatter();
        coinFormatter.setCurrencyPlural("coins");
        coinFormatter.setCurrencySingular("coin");
        coinFormatter.setFractionDigits(0);
        coinFormatter.setDisplayName("Coin");
        coinFormatter.setShowGroupSeparator(false);
        coinFormatter.setStartBalance(0);

        Map<String, CoinFormatter> map = new HashMap<>();
        map.put("point", pointFormatter);
        map.put("coin", coinFormatter);
        return map;
    }

    @ConfigPath({"storage", "type"})
    default String getStorageType() {
        return "yaml";
    }

    @ConfigPath({"storage", "urgent-load"})
    default boolean isStorageUrgentLoad() {
        return true;
    }

    @ConfigPath({"storage", "save", "delay"})
    default int getStorageSaveDelay() {
        return 20;
    }

    @ConfigPath({"storage", "save", "entry-per-tick"})
    default int getStorageSaveEntryPerTick() {
        return 1;
    }
}

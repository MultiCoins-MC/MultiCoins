package me.hsgamer.multicoins.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.multicoins.object.CoinFormatter;

import java.util.Collections;
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
        return Collections.singletonMap("point", pointFormatter);
    }

    @ConfigPath("save.delay")
    default int getSaveDelay() {
        return 20;
    }

    @ConfigPath("save.entry-per-tick")
    default int getSaveEntryPerTick() {
        return 1;
    }

    @ConfigPath("storage.type")
    default String getStorageType() {
        return "yaml";
    }

    @ConfigPath("storage.urgent-load")
    default boolean isStorageUrgentLoad() {
        return true;
    }
}

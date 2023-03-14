package me.hsgamer.multicoins.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.multicoins.object.CoinFormatter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface MainConfig {
    @ConfigPath("coins")
    default List<String> getCoins() {
        return Collections.emptyList();
    }

    @ConfigPath("negative-allowed-coins")
    default List<String> getNegativeAllowedCoins() {
        return Collections.emptyList();
    }

    @ConfigPath("formatters")
    default Map<String, CoinFormatter> getFormatters() {
        return Collections.emptyMap();
    }

    @ConfigPath("start-balances")
    default Map<String, Double> getStartBalances() {
        return Collections.emptyMap();
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

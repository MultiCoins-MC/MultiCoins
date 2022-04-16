package me.hsgamer.multicoins.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.path.AdvancedConfigPath;
import me.hsgamer.hscore.config.path.ConfigPath;
import me.hsgamer.hscore.config.path.StickyConfigPath;
import me.hsgamer.hscore.config.path.impl.IntegerConfigPath;
import me.hsgamer.hscore.config.path.impl.SimpleConfigPath;
import me.hsgamer.hscore.config.path.impl.StringConfigPath;
import me.hsgamer.multicoins.object.CoinFormatter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainConfig extends PathableConfig {
    public static final SimpleConfigPath<List<String>> COINS = new SimpleConfigPath<>("coins", Collections.emptyList());
    public static final SimpleConfigPath<List<String>> NEGATIVE_ALLOWED_COINS = new SimpleConfigPath<>("negative-allowed-coins", Collections.emptyList());
    public static final ConfigPath<Map<String, CoinFormatter>> FORMATTERS = new AdvancedConfigPath<Map<String, Map<String, Object>>, Map<String, CoinFormatter>>("formatters", Collections.emptyMap()) {
        @Override
        public @NotNull Map<String, Map<String, Object>> getFromConfig(@NotNull Config config) {
            Map<String, Map<String, Object>> map = new HashMap<>();
            config.getKeys(getPath(), false).forEach(key -> map.put(key, config.getNormalizedValues(getPath() + "." + key, false)));
            return map;
        }

        @Override
        public @NotNull Map<String, CoinFormatter> convert(@NotNull Map<String, Map<String, Object>> rawValue) {
            Map<String, CoinFormatter> map = new HashMap<>();
            rawValue.forEach((key, value) -> map.put(key, new CoinFormatter(value)));
            return map;
        }

        @Override
        public @NotNull Map<String, Map<String, Object>> convertToRaw(@NotNull Map<String, CoinFormatter> value) {
            Map<String, Map<String, Object>> map = new HashMap<>();
            value.forEach((key, formatter) -> map.put(key, formatter.toMap()));
            return map;
        }
    };
    public static final ConfigPath<Map<String, Double>> START_BALANCES = new StickyConfigPath<>(new AdvancedConfigPath<Map<String, Object>, Map<String, Double>>("start-balances", Collections.emptyMap()) {
        @Override
        public @Nullable Map<String, Object> getFromConfig(@NotNull Config config) {
            return config.getNormalizedValues(getPath(), false);
        }

        @Override
        public @NotNull Map<String, Double> convert(@NotNull Map<String, Object> rawValue) {
            Map<String, Double> map = new HashMap<>();
            rawValue.forEach((key, value) -> map.put(key, Double.parseDouble(value.toString())));
            return map;
        }

        @Override
        public @NotNull Map<String, Object> convertToRaw(@NotNull Map<String, Double> value) {
            return new HashMap<>(value);
        }
    });

    public static final IntegerConfigPath SAVE_DELAY = new IntegerConfigPath("save.delay", 20);
    public static final IntegerConfigPath SAVE_ENTRY_PER_TICK = new IntegerConfigPath("save.entry-per-tick", 1);
    public static final StringConfigPath STORAGE_TYPE = new StringConfigPath("storage.type", "yaml");

    public MainConfig(Plugin plugin) {
        super(new BukkitConfig(plugin, "config.yml"));
    }
}

package me.hsgamer.multicoins;

import com.google.common.reflect.TypeToken;
import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import me.hsgamer.multicoins.command.MainCommand;
import me.hsgamer.multicoins.config.MainConfig;
import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.listener.PlayerListener;
import me.hsgamer.multicoins.manager.CoinManager;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.topper.spigot.config.DefaultConverterRegistry;
import me.hsgamer.topper.spigot.config.converter.StringMapConverter;
import me.hsgamer.topper.spigot.number.NumberStorageBuilder;

import java.io.File;
import java.util.*;

public final class MultiCoins extends BasePlugin {
    static {
        DefaultConverterRegistry.register();

        //noinspection UnstableApiUsage
        DefaultConverterRegistry.register(new TypeToken<Map<String, CoinFormatter>>() {
        }, new StringMapConverter<CoinFormatter>() {
            @Override
            protected CoinFormatter toValue(Object o) {
                if (o instanceof Map) {
                    Map<String, Object> map = new HashMap<>();
                    for (Map.Entry<?, ?> entry : ((Map<?, ?>) o).entrySet()) {
                        map.put(Objects.toString(entry.getKey()), entry.getValue());
                    }
                    return new CoinFormatter(map);
                }
                return null;
            }

            @Override
            protected Object toRawValue(Object o) {
                if (o instanceof CoinFormatter) {
                    return ((CoinFormatter) o).toMap();
                }
                return null;
            }
        });
        //noinspection UnstableApiUsage
        DefaultConverterRegistry.register(new TypeToken<Map<String, Double>>() {
        }, new StringMapConverter<Double>() {
            @Override
            protected Double toValue(Object o) {
                try {
                    return Double.parseDouble(Objects.toString(o));
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            @Override
            protected Object toRawValue(Object o) {
                return o;
            }
        });
    }

    private final NumberStorageBuilder numberStorageBuilder = new NumberStorageBuilder(this, new File(getDataFolder(), "coins"));
    private final MainConfig mainConfig = ConfigGenerator.newInstance(MainConfig.class, new BukkitConfig(this, "config.yml"));
    private final MessageConfig messageConfig = ConfigGenerator.newInstance(MessageConfig.class, new BukkitConfig(this, "messages.yml"));
    private final CoinManager coinManager = new CoinManager(this);

    @Override
    public void load() {
        MessageUtils.setPrefix(messageConfig::getPrefix);
    }

    @Override
    public void enable() {
        coinManager.setup();
        registerListener(new PlayerListener(this));
        registerCommand(new MainCommand(this));
    }

    @Override
    public void disable() {
        coinManager.disable();
    }

    @Override
    protected List<Class<?>> getPermissionClasses() {
        return Collections.singletonList(Permissions.class);
    }

    public NumberStorageBuilder getNumberStorageBuilder() {
        return numberStorageBuilder;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public CoinManager getCoinManager() {
        return coinManager;
    }
}

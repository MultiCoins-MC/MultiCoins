package me.hsgamer.multicoins;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.command.MainCommand;
import me.hsgamer.multicoins.config.MainConfig;
import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.expansion.CoinsExpansion;
import me.hsgamer.multicoins.listener.JoinListener;
import me.hsgamer.multicoins.manager.CoinManager;
import me.hsgamer.topper.spigot.config.DatabaseConfig;

public final class MultiCoins extends BasePlugin {
    private final MainConfig mainConfig = new MainConfig(this);
    private final DatabaseConfig databaseConfig = new DatabaseConfig(this);
    private final MessageConfig messageConfig = new MessageConfig(this);
    private final CoinManager coinManager = new CoinManager(this);

    @Override
    public void load() {
        MessageUtils.setPrefix(MessageConfig.PREFIX::getValue);
        mainConfig.setup();
        databaseConfig.setup();
        messageConfig.setup();
    }

    @Override
    public void enable() {
        Permissions.register();

        coinManager.setup();
        registerListener(new JoinListener(this));
        registerCommand(new MainCommand(this));

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            CoinsExpansion expansion = new CoinsExpansion(this);
            expansion.register();
            addDisableFunction(expansion::unregister);
        }
    }

    @Override
    public void disable() {
        coinManager.disable();
        Permissions.unregister();
    }

    public CoinManager getCoinManager() {
        return coinManager;
    }
}

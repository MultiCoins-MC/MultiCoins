package me.hsgamer.multicoins.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.path.impl.StringConfigPath;
import org.bukkit.plugin.Plugin;

public class MessageConfig extends PathableConfig {
    public static final StringConfigPath PREFIX = new StringConfigPath("prefix", "&7[&dMulticoins&7] &r");
    public static final StringConfigPath ARG_NOT_FOUND = new StringConfigPath("arg-not-found", "&cArgument not found!");
    public static final StringConfigPath PLAYER_ONLY = new StringConfigPath("player-only", "&cYou can only use this command in game!");
    public static final StringConfigPath INVALID_NUMBER = new StringConfigPath("invalid-number", "&cInvalid number!");
    public static final StringConfigPath HOLDER_NOT_FOUND = new StringConfigPath("holder-not-found", "&cHolder not found!");
    public static final StringConfigPath GIVE_SUCCESS = new StringConfigPath("give-success", "&aSuccessfully given {value} {currency} to {name}!");
    public static final StringConfigPath GIVE_FAILED = new StringConfigPath("give-failed", "&cFailed to give {value} {currency} to {name}!");
    public static final StringConfigPath TAKE_SUCCESS = new StringConfigPath("take-success", "&aSuccessfully taken {value} {currency} from {name}!");
    public static final StringConfigPath TAKE_FAILED = new StringConfigPath("take-failed", "&cFailed to take {value} {currency} from {name}!");
    public static final StringConfigPath SET_SUCCESS = new StringConfigPath("set-success", "&aSuccessfully set {value} {currency} to {name}!");
    public static final StringConfigPath SET_FAILED = new StringConfigPath("set-failed", "&cFailed to set {value} {currency} to {name}!");
    public static final StringConfigPath BALANCE = new StringConfigPath("balance", "&aBalance: &f{value} {currency}");

    public MessageConfig(Plugin plugin) {
        super(new BukkitConfig(plugin, "messages.yml"));
    }
}

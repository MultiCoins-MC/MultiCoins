package me.hsgamer.multicoins.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface MessageConfig {
    @ConfigPath("prefix")
    default String getPrefix() {
        return "&7[&dMultiCoins&7] &r";
    }

    @ConfigPath("player-only")
    default String getPlayerOnly() {
        return "&cYou can only use this command in game!";
    }

    @ConfigPath("invalid-number")
    default String getInvalidNumber() {
        return "&cInvalid number!";
    }

    @ConfigPath("holder-not-found")
    default String getHolderNotFound() {
        return "&cHolder not found!";
    }

    @ConfigPath("give-success")
    default String getGiveSuccess() {
        return "&aSuccessfully given {value} {currency} &ato {name}!";
    }

    @ConfigPath("give-failed")
    default String getGiveFailed() {
        return "&cFailed to give {value} {currency} &cto {name}!";
    }

    @ConfigPath("take-success")
    default String getTakeSuccess() {
        return "&aSuccessfully taken {value} {currency} &afrom {name}!";
    }

    @ConfigPath("take-failed")
    default String getTakeFailed() {
        return "&cFailed to take {value} {currency} &cfrom {name}!";
    }

    @ConfigPath("set-success")
    default String getSetSuccess() {
        return "&aSuccessfully set {value} {currency} &ato {name}!";
    }

    @ConfigPath("set-failed")
    default String getSetFailed() {
        return "&cFailed to set {value} {currency} &cto {name}!";
    }

    @ConfigPath("balance")
    default String getBalance() {
        return "&aBalance: &f{value} {currency}";
    }
}

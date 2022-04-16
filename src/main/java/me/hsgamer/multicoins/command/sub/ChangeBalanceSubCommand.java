package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.Permissions;
import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.object.CoinEntry;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public abstract class ChangeBalanceSubCommand extends SubCommand {
    protected final MultiCoins instance;

    protected ChangeBalanceSubCommand(MultiCoins instance, @NotNull String name, @NotNull String description) {
        super(name, description, "/multicoins " + name + " <holder> <player> <amount>", Permissions.SET.getName(), true);
        this.instance = instance;
    }

    protected abstract boolean tryChange(CommandSender sender, CoinEntry entry, double amount);

    protected abstract void sendSuccessMessage(CommandSender sender, CoinEntry entry, double amount, CoinFormatter formatter);

    protected abstract void sendFailMessage(CommandSender sender, CoinEntry entry, double amount, CoinFormatter formatter);

    @SuppressWarnings("deprecation")
    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        Optional<CoinHolder> optionalCoinHolder = instance.getCoinManager().getHolder(args[0]);
        if (!optionalCoinHolder.isPresent()) {
            MessageUtils.sendMessage(sender, MessageConfig.HOLDER_NOT_FOUND.getValue());
            return;
        }
        CoinHolder coinHolder = optionalCoinHolder.get();
        //noinspection deprecation
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        CoinEntry entry = coinHolder.getOrCreateEntry(offlinePlayer.getUniqueId());
        Optional<Double> amountOptional = Validate.getNumber(args[2]).map(BigDecimal::doubleValue);
        if (!amountOptional.isPresent()) {
            MessageUtils.sendMessage(sender, MessageConfig.INVALID_NUMBER.getValue());
            return;
        }
        double amount = amountOptional.get();
        CoinFormatter formatter = instance.getCoinManager().getFormatter(coinHolder.getName());
        if (tryChange(sender, entry, amount)) {
            sendSuccessMessage(sender, entry, amount, formatter);
        } else {
            sendFailMessage(sender, entry, amount, formatter);
        }
    }

    @Override
    public boolean isProperUsage(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return args.length >= 3;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return CommandUtils.queryTabComplete(instance, args);
    }
}

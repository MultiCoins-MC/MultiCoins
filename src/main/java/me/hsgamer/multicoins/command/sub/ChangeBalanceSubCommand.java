package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.Permissions;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.topper.core.entry.DataEntry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class ChangeBalanceSubCommand extends SubCommand {
    protected final MultiCoins instance;

    protected ChangeBalanceSubCommand(MultiCoins instance, @NotNull String name, @NotNull String description) {
        super(name, description, "/<label> " + name + " <holder> <player> <amount>", Permissions.SET.getName(), true);
        this.instance = instance;
    }

    protected abstract boolean tryChange(CommandSender sender, CoinHolder holder, UUID uuid, double amount);

    protected abstract void sendSuccessMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount);

    protected abstract void sendFailMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount);

    @SuppressWarnings("deprecation")
    @Override
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        Optional<CoinHolder> optionalCoinHolder = instance.getCoinManager().getHolder(args[0]);
        if (!optionalCoinHolder.isPresent()) {
            MessageUtils.sendMessage(sender, instance.getMessageConfig().getHolderNotFound());
            return;
        }
        CoinHolder coinHolder = optionalCoinHolder.get();
        //noinspection deprecation
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        DataEntry<Double> entry = coinHolder.getOrCreateEntry(offlinePlayer.getUniqueId());
        Optional<Double> amountOptional = Validate.getNumber(args[2]).map(BigDecimal::doubleValue);
        if (!amountOptional.isPresent()) {
            MessageUtils.sendMessage(sender, instance.getMessageConfig().getInvalidNumber());
            return;
        }
        double amount = amountOptional.get();
        if (tryChange(sender, coinHolder, entry.getUuid(), amount)) {
            sendSuccessMessage(sender, coinHolder, entry.getUuid(), amount);
        } else {
            sendFailMessage(sender, coinHolder, entry.getUuid(), amount);
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

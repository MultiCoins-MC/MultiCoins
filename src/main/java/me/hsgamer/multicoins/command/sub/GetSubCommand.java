package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.command.sub.SubCommand;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.Permissions;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import me.hsgamer.topper.core.entry.DataEntry;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class GetSubCommand extends SubCommand {
    private final MultiCoins instance;

    public GetSubCommand(MultiCoins instance) {
        super("get", "Get the amount of coins from the player", "/<label> get <holder> [player]", Permissions.GET.getName(), true);
        this.instance = instance;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onSubCommand(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        Optional<CoinHolder> optionalCoinHolder = instance.getCoinManager().getHolder(args[0]);
        if (!optionalCoinHolder.isPresent()) {
            MessageUtils.sendMessage(sender, instance.getMessageConfig().getHolderNotFound());
            return;
        }
        CoinHolder coinHolder = optionalCoinHolder.get();

        Object target = sender;
        if (args.length > 1) {
            //noinspection deprecation
            target = Bukkit.getOfflinePlayer(args[1]);
        }
        if (!(target instanceof OfflinePlayer)) {
            MessageUtils.sendMessage(sender, instance.getMessageConfig().getPlayerOnly());
            return;
        }
        OfflinePlayer offlinePlayer = (OfflinePlayer) target;

        DataEntry<Double> entry = coinHolder.getOrCreateEntry(offlinePlayer.getUniqueId());
        CoinFormatter formatter = instance.getCoinManager().getFormatter(coinHolder.getName());
        MessageUtils.sendMessage(sender, formatter.replace(instance.getMessageConfig().getBalance(), entry.getUuid(), entry.getValue()));
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String... args) {
        return CommandUtils.queryTabComplete(instance, args);
    }
}

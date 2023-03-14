package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class GiveSubCommand extends ChangeBalanceSubCommand {
    public GiveSubCommand(MultiCoins instance) {
        super(instance, "give", "Give coin to the player");
    }

    @Override
    protected boolean tryChange(CommandSender sender, CoinHolder holder, UUID uuid, double amount) {
        return holder.giveBalance(uuid, amount);
    }

    @Override
    protected void sendSuccessMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount, CoinFormatter formatter) {
        MessageUtils.sendMessage(sender, formatter.replace(instance.getMessageConfig().getGiveSuccess(), uuid, amount));
    }

    @Override
    protected void sendFailMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount, CoinFormatter formatter) {
        MessageUtils.sendMessage(sender, formatter.replace(instance.getMessageConfig().getGiveFailed(), uuid, amount));
    }
}

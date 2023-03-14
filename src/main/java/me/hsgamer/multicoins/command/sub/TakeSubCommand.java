package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.object.CoinHolder;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class TakeSubCommand extends ChangeBalanceSubCommand {
    public TakeSubCommand(MultiCoins instance) {
        super(instance, "take", "Take coin from the player");
    }

    @Override
    protected boolean tryChange(CommandSender sender, CoinHolder holder, UUID uuid, double amount) {
        return holder.takeBalance(uuid, amount);
    }

    @Override
    protected void sendSuccessMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount) {
        MessageUtils.sendMessage(sender, holder.getCoinFormatter().replace(instance.getMessageConfig().getTakeSuccess(), uuid, amount));
    }

    @Override
    protected void sendFailMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount) {
        MessageUtils.sendMessage(sender, holder.getCoinFormatter().replace(instance.getMessageConfig().getTakeFailed(), uuid, amount));
    }
}

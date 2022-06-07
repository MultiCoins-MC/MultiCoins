package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class SetSubCommand extends ChangeBalanceSubCommand {
    public SetSubCommand(MultiCoins instance) {
        super(instance, "set", "Set coin to the player");
    }

    @Override
    protected boolean tryChange(CommandSender sender, CoinHolder holder, UUID uuid, double amount) {
        return holder.setBalance(uuid, amount);
    }

    @Override
    protected void sendSuccessMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount, CoinFormatter formatter) {
        MessageUtils.sendMessage(sender, formatter.replace(MessageConfig.SET_SUCCESS.getValue(), uuid, amount));
    }

    @Override
    protected void sendFailMessage(CommandSender sender, CoinHolder holder, UUID uuid, double amount, CoinFormatter formatter) {
        MessageUtils.sendMessage(sender, formatter.replace(MessageConfig.SET_FAILED.getValue(), uuid, amount));
    }
}

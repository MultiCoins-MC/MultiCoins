package me.hsgamer.multicoins.command.sub;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.config.MessageConfig;
import me.hsgamer.multicoins.object.CoinEntry;
import me.hsgamer.multicoins.object.CoinFormatter;
import org.bukkit.command.CommandSender;

public class TakeSubCommand extends ChangeBalanceSubCommand {
    public TakeSubCommand(MultiCoins instance) {
        super(instance, "take", "Take coin from the player");
    }

    @Override
    protected boolean tryChange(CommandSender sender, CoinEntry entry, double amount) {
        return entry.takeBalance(amount);
    }

    @Override
    protected void sendSuccessMessage(CommandSender sender, CoinEntry entry, double amount, CoinFormatter formatter) {
        MessageUtils.sendMessage(sender, formatter.replace(MessageConfig.TAKE_SUCCESS.getValue(), entry.getUuid(), amount));
    }

    @Override
    protected void sendFailMessage(CommandSender sender, CoinEntry entry, double amount, CoinFormatter formatter) {
        MessageUtils.sendMessage(sender, formatter.replace(MessageConfig.TAKE_FAILED.getValue(), entry.getUuid(), amount));
    }
}

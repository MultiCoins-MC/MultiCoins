package me.hsgamer.multicoins.command;

import me.hsgamer.hscore.bukkit.command.sub.SubCommandManager;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.command.sub.GetSubCommand;
import me.hsgamer.multicoins.command.sub.GiveSubCommand;
import me.hsgamer.multicoins.command.sub.SetSubCommand;
import me.hsgamer.multicoins.command.sub.TakeSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainCommand extends Command {
    private final SubCommandManager subCommandManager;

    public MainCommand(MultiCoins instance) {
        super(instance.getName().toLowerCase(Locale.ROOT), "Main Command", "/" + instance.getName().toLowerCase(Locale.ROOT), Collections.singletonList("coins"));
        this.subCommandManager = new SubCommandManager();
        subCommandManager.registerSubcommand(new GiveSubCommand(instance));
        subCommandManager.registerSubcommand(new TakeSubCommand(instance));
        subCommandManager.registerSubcommand(new SetSubCommand(instance));
        subCommandManager.registerSubcommand(new GetSubCommand(instance));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
        return subCommandManager.onCommand(sender, commandLabel, args);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return subCommandManager.onTabComplete(sender, alias, args);
    }
}

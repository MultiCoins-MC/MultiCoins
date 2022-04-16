package me.hsgamer.multicoins.command.sub;

import me.hsgamer.multicoins.MultiCoins;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class CommandUtils {
    private CommandUtils() {
        // EMPTY
    }

    static @NotNull List<String> queryTabComplete(MultiCoins instance, @NotNull String[] args) {
        if (args.length == 1) {
            String holder = args[0];
            return instance.getCoinManager().getHolders()
                    .stream()
                    .filter(holderName -> holder.isEmpty() || holderName.startsWith(holder))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            String name = args[1].trim();
            return Bukkit.getOnlinePlayers().stream()
                    .map(HumanEntity::getName)
                    .filter(playerName -> name.isEmpty() || playerName.startsWith(name))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

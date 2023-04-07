package me.hsgamer.multicoins.command.sub;

import me.hsgamer.multicoins.MultiCoins;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class CommandUtils {
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
            return Arrays.stream(Bukkit.getOfflinePlayers())
                    .map(OfflinePlayer::getName)
                    .filter(Objects::nonNull)
                    .filter(playerName -> name.isEmpty() || playerName.startsWith(name))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

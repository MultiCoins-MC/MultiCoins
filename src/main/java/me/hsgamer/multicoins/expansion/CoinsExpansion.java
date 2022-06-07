package me.hsgamer.multicoins.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.object.CoinFormatter;
import me.hsgamer.multicoins.object.CoinHolder;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;

public class CoinsExpansion extends PlaceholderExpansion {
    private final MultiCoins instance;

    public CoinsExpansion(MultiCoins instance) {
        this.instance = instance;
    }

    @Override
    public @NotNull String getIdentifier() {
        return instance.getName().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", instance.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] split = params.split(";");
        String name = split[0].trim();
        String type = split.length > 1 ? split[1].toLowerCase(Locale.ROOT).trim() : "";
        Optional<CoinHolder> optionalCoinHolder = instance.getCoinManager().getHolder(name);
        if (!optionalCoinHolder.isPresent()) return null;
        CoinHolder coinHolder = optionalCoinHolder.get();
        CoinFormatter formatter = instance.getCoinManager().getFormatter(name);
        switch (type) {
            case "currency":
                double amount;
                if (split.length > 2) {
                    try {
                        amount = Double.parseDouble(split[2].trim());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                } else {
                    amount = coinHolder.getBalance(player.getUniqueId());
                }
                return formatter.getCurrency(amount);
            case "value_raw":
                return String.valueOf(coinHolder.getBalance(player.getUniqueId()));
            case "value":
            default:
                return formatter.format(coinHolder.getBalance(player.getUniqueId()));
        }
    }
}

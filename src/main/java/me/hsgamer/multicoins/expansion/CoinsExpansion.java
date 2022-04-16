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
        Optional<CoinHolder> optionalCoinHolder = instance.getCoinManager().getHolder(params);
        if (!optionalCoinHolder.isPresent()) return null;
        CoinHolder coinHolder = optionalCoinHolder.get();
        CoinFormatter formatter = instance.getCoinManager().getFormatter(params);
        return formatter.format(coinHolder.getOrCreateEntry(player.getUniqueId()).getBalance());
    }
}

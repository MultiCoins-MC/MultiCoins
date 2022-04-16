package me.hsgamer.multicoins.builder;

import me.hsgamer.hscore.builder.Builder;
import me.hsgamer.multicoins.MultiCoins;
import me.hsgamer.multicoins.object.CoinStorage;
import me.hsgamer.multicoins.storage.YamlStorage;

public class CoinStorageBuilder extends Builder<MultiCoins, CoinStorage> {
    public static final CoinStorageBuilder INSTANCE = new CoinStorageBuilder();

    private CoinStorageBuilder() {
        register(plugin -> new YamlStorage(), "yaml", "yml");
    }
}

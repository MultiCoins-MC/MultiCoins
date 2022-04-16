package me.hsgamer.multicoins.object;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public interface CoinStorage {
    Logger LOGGER = Logger.getLogger(CoinStorage.class.getName());

    CompletableFuture<Map<UUID, Double>> load(CoinHolder holder);

    CompletableFuture<Void> save(CoinEntry topEntry, boolean onUnregister);

    default void onRegister(CoinHolder holder) {
        // EMPTY
    }

    default void onUnregister(CoinHolder holder) {
        // EMPTY
    }
}

package me.hsgamer.multicoins.listener;

import me.hsgamer.multicoins.MultiCoins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class JoinListener implements Listener {
    private final MultiCoins instance;

    public JoinListener(MultiCoins instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPreJoin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            instance.getCoinManager().create(event.getUniqueId());
        }
    }
}

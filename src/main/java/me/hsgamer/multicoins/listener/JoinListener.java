package me.hsgamer.multicoins.listener;

import me.hsgamer.multicoins.MultiCoins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final MultiCoins instance;

    public JoinListener(MultiCoins instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        instance.getCoinManager().create(event.getPlayer().getUniqueId());
    }
}

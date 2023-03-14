package me.hsgamer.multicoins.listener;

import me.hsgamer.multicoins.MultiCoins;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final MultiCoins instance;

    public PlayerListener(MultiCoins instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        instance.getCoinManager().create(event.getPlayer().getUniqueId());
    }
}

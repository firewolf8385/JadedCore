package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.events.LobbyQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangeWorldListener implements Listener {
    private final JadedCorePlugin plugin;

    public PlayerChangeWorldListener(final JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if(!plugin.getLobbyManager().isLobbyWorld(event.getFrom())) {
            return;
        }

        // Call the LobbyQuitEvent event.
        plugin.getServer().getPluginManager().callEvent(new LobbyQuitEvent(event.getPlayer()));
    }
}
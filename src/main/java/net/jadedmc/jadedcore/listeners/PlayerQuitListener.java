package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.events.LobbyQuitEvent;
import net.jadedmc.jadedcore.player.JadedPlayer;
import net.jadedmc.jadedsync.api.server.InstanceStatus;
import net.jadedmc.jadedutils.chat.ChatUtils;
import net.jadedmc.jadedutils.scoreboard.CustomScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listens to the PlayerQuitEvent, which is called when a player quits.
 * Used to remove references to the player object when the player leaves.
 */
public class PlayerQuitListener implements Listener {
    private final JadedCorePlugin plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerQuitListener(JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the PlayerQuitEvent is called.
     * @param event PlayerQuitEvent.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        JadedPlayer jadedPlayer = plugin.getJadedPlayerManager().getPlayer(player);

        // Call the lobby quit event if the world is a lobby world.
        if(plugin.getLobbyManager().isLobbyWorld(player.getWorld())) {
            plugin.getServer().getPluginManager().callEvent(new LobbyQuitEvent(player));
        }

        event.quitMessage(null);

        if(!jadedPlayer.isVanished()) {
            ChatUtils.broadcast(player.getWorld(),"&8[&c-&8] &c" + jadedPlayer.getName());
        }

        plugin.getJadedPlayerManager().removePlayer(player);

        // Remove cached scoreboard from a player.
        CustomScoreboard.removePlayer(player.getUniqueId());
    }
}
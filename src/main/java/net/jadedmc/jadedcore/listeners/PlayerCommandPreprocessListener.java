package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.player.JadedPlayer;
import net.jadedmc.jadedutils.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Listens to the PlayerCommandPreprocessEvent event, which runs every time a player sends a command.
 * Used to allow staff to see commands used with /commandspy.
 */
public class PlayerCommandPreprocessListener implements Listener {
    private final JadedCorePlugin plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerCommandPreprocessListener(JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the PlayerCommandPreprocessEvent is called.
     * @param event PlayerCommandPreprocessEvent.
     */
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage();

        for(JadedPlayer jadedPlayer : plugin.getJadedPlayerManager().getJadedPlayers()) {
            if(jadedPlayer.isSpying()) {
                ChatUtils.chat(jadedPlayer.getPlayer(), "&7[&aSpy&7] &a" + player.getName() + ": &f" + cmd);
            }
        }
    }
}
package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.events.JadedJoinEvent;
import net.jadedmc.jadedcore.player.JadedPlayer;
import net.jadedmc.jadedcore.player.Rank;
import net.jadedmc.jadedutils.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Listens to the PlayerJoinEvent, which is called when a player joins the server.
 * Used to load the JadedPlayer of that player.
 */
public class PlayerJoinListener implements Listener {
    private final JadedCorePlugin plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerJoinListener(JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the PlayerJoinEvent is called.
     * @param event PlayerJoinEvent.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        // Hide all vanished players.
        for(JadedPlayer otherPlayer : plugin.getJadedPlayerManager().getJadedPlayers()) {
            if(otherPlayer.isVanished()) {
                player.hidePlayer(plugin, otherPlayer.getPlayer());
            }
        }

        // Load the JadedPlayer.
        plugin.getJadedPlayerManager().addPlayer(player).thenAccept(jadedPlayer -> {

            // Join Message
            if(plugin.getLobbyManager().isEnabled() && !jadedPlayer.isVanished()) {
                final Rank rank = jadedPlayer.getRank();
                switch (rank) {
                    //case AMETHYST -> ChatUtils.broadcast(player.getWorld(), "&5>&f>&5> &lAmethyst &7" + jadedPlayer.getName() + " &ahas joined the lobby! &5<&f<&5<");
                    //case SAPPHIRE -> ChatUtils.broadcast(player.getWorld(), "&9>&f>&9> &lSapphire &7" + jadedPlayer.getName() + " &ahas joined the lobby! &9<&f<&9<");
                    //case JADED -> ChatUtils.broadcast(player.getWorld(), "&a>&f>&a> &lJaded &7" + jadedPlayer.getName() + " &ahas joined the lobby! &a<&f<&a<");
                    case AMETHYST, SAPPHIRE, JADED -> ChatUtils.broadcast(player.getWorld(), rank.getRankColor() + "><white>></white>> " + rank.getDisplayName() + " <gray>" + jadedPlayer.getName() + " <green>has joined the lobby! " + rank.getRankColor() + "<<white><</white><");
                    default -> ChatUtils.broadcast(player.getWorld(), "&8[&a+&8] &a" + jadedPlayer.getName());
                }
            }

            // Give the "A Whole New World" achievement.
            plugin.getAchievementManager().getAchievement("general_1").unlock(player);

            // Give the "Veteran" achievement.
            if(System.currentTimeMillis() - jadedPlayer.getFirstJoined().getTime() > Long.parseLong("31556952000")) {
                plugin.getAchievementManager().getAchievement("general_5").unlock(player);
            }

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                // If vanished, hide the player from all other players.
                if(jadedPlayer.isVanished()) {
                    for(Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
                        otherPlayer.hidePlayer(plugin, player);
                    }
                }

                plugin.getServer().getPluginManager().callEvent(new JadedJoinEvent(jadedPlayer));

                // Sends the player to the lobby if it is enabled.
                if(plugin.getLobbyManager().isEnabled()) {
                    plugin.getLobbyManager().sendToLocalLobby(player);
                }
            });
        });
    }
}
package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.guis.GamesGUI;
import net.jadedmc.jadedcore.guis.ProfileGUI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listens to the PlayerInteractEvent event, which runs every time a player interacts with something.
 * Used to see if the player is trying to use one of the "CustomItems", such as the Game menu.
 */
public class PlayerInteractListener implements Listener {
    private final JadedCorePlugin plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public PlayerInteractListener(final JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        // Exit if the item is null.
        if(event.getItem() == null)
            return;

        // Exit if item meta is null.
        if(event.getItem().getItemMeta() == null)
            return;

        String item = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());

        if(item == null) {
            return;
        }

        switch (item) {
            case "Games" -> {
                new GamesGUI(plugin).open(player);
                event.setCancelled(true);
            }
            case "Profile" -> {
                new ProfileGUI(plugin, player).open(player);
                event.setCancelled(true);
            }
            case "Cosmetics" -> {
                player.performCommand("uc menu");
                event.setCancelled(true);
            }
        }
    }
}
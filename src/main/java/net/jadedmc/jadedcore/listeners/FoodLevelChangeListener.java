package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    private final JadedCorePlugin plugin;

    public FoodLevelChangeListener(JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when an entity's food level changes.
     * @param event Food Level Change Event.
     */
    @EventHandler
    public void onEvent(FoodLevelChangeEvent event) {
        // Make sure the world is a lobby world.
        if(!plugin.getLobbyManager().isLobbyWorld(event.getEntity().getWorld())) {
            return;
        }

        // Fill the hunger bar and cancel the event.
        event.getEntity().setFoodLevel(20);
        event.setCancelled(true);
    }
}
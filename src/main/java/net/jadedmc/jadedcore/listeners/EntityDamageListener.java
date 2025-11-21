package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private final JadedCorePlugin plugin;

    public EntityDamageListener(JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player player)) {
            return;
        }

        if(plugin.getLobbyManager().isLobbyWorld(player.getWorld())) {
            event.setCancelled(true);

            if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                player.teleport(plugin.getLobbyManager().getSpawn());
            }
        }
    }
}
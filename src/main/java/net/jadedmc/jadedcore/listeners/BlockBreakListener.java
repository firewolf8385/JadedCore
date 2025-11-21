package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedCorePlugin;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private final JadedCorePlugin plugin;

    public BlockBreakListener(JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if(plugin.getLobbyManager().isLobbyWorld(player.getWorld())) {
            event.setCancelled(true);
        }
    }
}
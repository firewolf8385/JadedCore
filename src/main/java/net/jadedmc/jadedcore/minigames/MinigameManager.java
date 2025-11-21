package net.jadedmc.jadedcore.minigames;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedsync.api.JadedSyncAPI;
import net.jadedmc.jadedsync.api.server.ServerInstance;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MinigameManager {
    private final JadedCorePlugin plugin;
    private final Map<Minigame, Integer> playerCounts = new HashMap<>();

    public MinigameManager(@NotNull final JadedCorePlugin plugin) {
        this.plugin = plugin;

        // Update player counts every 30 seconds.
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            JadedSyncAPI.getInstancesAsync().thenAccept(instances -> {
                // Reset cached online count.
                playerCounts.clear();

                // Add back all the valid Minigames.
                for(final Minigame minigame : Minigame.values()) {
                    playerCounts.put(minigame, 0);
                }

                // Check every online instance.
                for(final ServerInstance instance : instances) {
                    final String json = instance.getIntegration("jadedcore");

                    if(json.isEmpty()) {
                        continue;
                    }

                    final Document integration = Document.parse(json);
                    final Minigame minigame = Minigame.valueOf(integration.getString("game"));


                    // Update per-mode player-count.
                    playerCounts.put(minigame, playerCounts.get(minigame) + instance.getOnline());
                }
            });
        },0, 30*20);
    }

    /**
     * Get the last known player count of a Minigame.
     * @param minigame Minigame to get player count of.
     * @return Player count of the Minigame.
     */
    public int getPlayerCount(final Minigame minigame) {
        return this.playerCounts.get(minigame);
    }
}

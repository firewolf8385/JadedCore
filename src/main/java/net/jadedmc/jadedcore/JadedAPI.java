package net.jadedmc.jadedcore;

import net.jadedmc.jadedcore.minigames.Minigame;
import net.jadedmc.jadedsync.api.JadedSyncAPI;
import net.jadedmc.jadedsync.api.server.ServerInstance;
import net.jadedmc.jadedutils.chat.StringUtils;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JadedAPI {
    private static JadedCorePlugin plugin;

    public static void initalize(JadedCorePlugin pl) {
        plugin = pl;
    }

    public static int getRequiredExp(int level) {
        return 10000 + (level * 2500);
    }

    public static String getLevelBar(final int currentExperience, final int currentLevel) {
        final int squareCount = 40;
        final int maxExperience = getRequiredExp(currentLevel);
        final int perSquare = maxExperience/squareCount;

        String bar = "&3";

        int squares = 0;
        for(int i = currentExperience; i > 0; i-= perSquare) {
            //bar += "■";
            bar += "|";
            squares++;
        }

        bar += "&7";

        for(int i = squares; i < squareCount; i++) {
            //bar += "■";
            bar += "|";
        }

        return StringUtils.translateLegacyMessage(bar);
    }

    public static void sendToLobby(@NotNull final Player player, final Minigame minigame) {
       JadedSyncAPI.getInstancesAsync().thenAccept(instances -> {
            String serverName = "";
            {
                int count = 999;

                // Loop through all online servers looking for a server to send the player to
                for (ServerInstance instance : instances) {
                    final String json = instance.getIntegration("jadedcore");

                    if(json.isEmpty()) {
                        System.out.println("JSON empty for " + instance.getName());
                        continue;
                    }

                    final Document integration = Document.parse(json);
                    final Minigame instanceMinigame = Minigame.valueOf(integration.getString("game"));
                    final boolean isLobby = integration.getBoolean("lobby");

                    // Make sure the server is the right mode
                    if (instanceMinigame != minigame) {
                        System.out.println("Looking for " + minigame + ", Found " + instanceMinigame);
                        continue;
                    }

                    // Make sure the server isn't a game server.
                    if (!isLobby) {
                        System.out.println(instance.getName() + " is not a lobby.");
                        continue;
                    }

                    // Make sure there is room for another game.
                    if (instance.getOnline() >= instance.getCapacity()) {
                        System.out.println("Not enough room!");
                        continue;
                    }

                    //
                    if (instance.getOnline() < count) {
                        count = instance.getOnline();
                        serverName = instance.getName();
                    }
                }

                // If no server is found, give up ¯\_(ツ)_/¯
                if (count == 999) {
                    System.out.println("No Server Found!");
                    return;
                }

                JadedSyncAPI.sendToServer(player, serverName);
            }
        }).whenComplete((results, error) -> error.printStackTrace());
    }
}
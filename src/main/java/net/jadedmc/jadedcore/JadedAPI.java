package net.jadedmc.jadedcore;

import net.jadedmc.jadedcore.achievements.Achievement;
import net.jadedmc.jadedcore.database.MongoDB;
import net.jadedmc.jadedcore.database.MySQL;
import net.jadedmc.jadedcore.minigames.Minigame;
import net.jadedmc.jadedcore.player.JadedPlayer;
import net.jadedmc.jadedsync.api.JadedSyncAPI;
import net.jadedmc.jadedsync.api.player.JadedSyncPlayer;
import net.jadedmc.jadedsync.api.player.JadedSyncPlayerMap;
import net.jadedmc.jadedsync.api.server.ServerInstance;
import net.jadedmc.jadedutils.chat.StringUtils;
import net.jadedmc.jadedutils.player.PluginPlayer;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class JadedAPI {
    private static JadedCorePlugin plugin;

    public static void initalize(JadedCorePlugin pl) {
        plugin = pl;
    }

    public static void createAchievement(final Minigame game, final String id, final String name, final String description, final int points, String... rewards) {
        plugin.getAchievementManager().createAchievement(game, id, name, description, points, rewards);
    }

    public static Achievement getAchievement(@NotNull final String id) {
        return plugin.getAchievementManager().getAchievement(id);
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

    public static JadedSyncPlayerMap getPlayers(final Minigame minigame) {
        final JadedSyncPlayerMap playerMap = new JadedSyncPlayerMap();

        for(JadedSyncPlayer player : JadedSyncAPI.getPlayers().values()) {
            final Document document = Document.parse(player.getIntegration("jadedcore"));

            if(Minigame.valueOf(document.getString("game")) == minigame) {
                playerMap.put(player.getUniqueId(), player);
            }
        }

        return playerMap;
    }

    public static MongoDB getMongoDB() {
        return plugin.getMongoDB();
    }

    public static MySQL getMySQL() {
        return plugin.getMySQL();
    }

    public static Minigame getMinigame() {
        return Minigame.valueOf(plugin.getConfigManager().getConfig().getString("serverGame"));
    }

    public static String getServerName() {
        return JadedSyncAPI.getCurrentInstance().getName();
    }

    public static JadedPlayer getJadedPlayer(@NotNull final Player player) {
        return plugin.getJadedPlayerManager().getPlayer(player);
    }

    public static JadedPlayer getJadedPlayer(@NotNull final PluginPlayer pluginPlayer) {
        return plugin.getJadedPlayerManager().getPlayer(pluginPlayer.getUniqueId());
    }

    public static JadedPlayer getJadedPlayer(@NotNull final UUID uuid) {
        return plugin.getJadedPlayerManager().getPlayer(uuid);
    }

    public static JadedCorePlugin getPlugin() {
        return plugin;
    }
}
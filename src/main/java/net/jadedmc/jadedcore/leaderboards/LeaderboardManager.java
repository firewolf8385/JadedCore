package net.jadedmc.jadedcore.leaderboards;

import net.jadedmc.jadedcore.JadedCorePlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages leaderboards for various statistics.
 */
public class LeaderboardManager {
    private final JadedCorePlugin plugin;
    private final Map<String, Integer> achievementPoints = new LinkedHashMap<>();

    /**
     * Creates the manager.
     * @param plugin Instance of the plugin.
     */
    public LeaderboardManager(@NotNull final JadedCorePlugin plugin) {
        this.plugin = plugin;

        // Creates a task that updates the leaderboards every 20 minutes
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::update, 20*4, 20*60*20);
    }

    /**
     * Updates the leaderboards.
     */
    public void update() {
        updateAchievementPoints();
    }

    public void updateAchievementPoints() {
        try {
            achievementPoints.clear();
            PreparedStatement statement = plugin.getMySQL().getConnection().prepareStatement("SELECT * FROM player_info ORDER BY achievementPoints DESC LIMIT 10");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                String player = resultSet.getString("username");
                int points = resultSet.getInt("achievementPoints");
                achievementPoints.put(player, points);
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Map<String, Integer> getAchievementPointsLeaderboard() {
        return achievementPoints;
    }
}
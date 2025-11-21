/*
 * This file is part of JadedCore, licensed under the MIT License.
 *
 *  Copyright (c) JadedMC
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.jadedcore;

import me.clip.placeholderapi.PlaceholderAPI;
import net.jadedmc.jadedcore.achievements.AchievementManager;
import net.jadedmc.jadedcore.commands.AbstractCommand;
import net.jadedmc.jadedcore.database.MongoDB;
import net.jadedmc.jadedcore.database.MySQL;
import net.jadedmc.jadedcore.database.Redis;
import net.jadedmc.jadedcore.leaderboards.LeaderboardManager;
import net.jadedmc.jadedcore.listeners.*;
import net.jadedmc.jadedcore.lobby.LobbyManager;
import net.jadedmc.jadedcore.minigames.MinigameManager;
import net.jadedmc.jadedcore.player.JadedPlayerManager;
import net.jadedmc.jadedcore.settings.ConfigManager;
import net.jadedmc.jadedcore.settings.JadedSyncIntegration;
import net.jadedmc.jadedsync.api.JadedSyncAPI;
import net.jadedmc.jadedutils.gui.GUIListeners;
import net.jadedmc.jadedutils.scoreboard.ScoreboardUpdate;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class JadedCorePlugin extends JavaPlugin {
    private AchievementManager achievementManager;
    private ConfigManager configManager;
    private JadedPlayerManager jadedPlayerManager;
    private LeaderboardManager leaderboardManager;
    private LobbyManager lobbyManager;
    private MinigameManager minigameManager;
    private MongoDB mongoDB;
    private MySQL mySQL;
    private Redis redis;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Load server settings.
        configManager = new ConfigManager(this);

        // Load Databases
        mongoDB = new MongoDB(this);
        mySQL = new MySQL(this);
        mySQL.openConnection();
        redis = new Redis(this);

        // Register JadedSync Integration
        JadedSyncAPI.registerIntegration(new JadedSyncIntegration(this));

        // Load other stuff
        jadedPlayerManager = new JadedPlayerManager(this);
        achievementManager = new AchievementManager(this);
        lobbyManager = new LobbyManager(this);
        leaderboardManager = new LeaderboardManager(this);
        minigameManager = new MinigameManager(this);

        AbstractCommand.registerCommands(this);

        registerListeners();

        // Updates scoreboards every second
        new ScoreboardUpdate().runTaskTimer(this, 20L, 20L);

        // Register placeholders.
        new Placeholders(this).register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Registers plugin listeners.
     */
    private void registerListeners() {
        // Plugin listeners.
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new ChannelMessageSendListener(this), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

        // Utility listeners.
        getServer().getPluginManager().registerEvents(new GUIListeners(), this);
    }

    public AchievementManager getAchievementManager() {
        return this.achievementManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public JadedPlayerManager getJadedPlayerManager() {
        return this.jadedPlayerManager;
    }

    public LeaderboardManager getLeaderboardManager() {
        return this.leaderboardManager;
    }

    public LobbyManager getLobbyManager() {
        return this.lobbyManager;
    }

    public MinigameManager getMinigameManager() {
        return this.minigameManager;
    }

    public MongoDB getMongoDB() {
        return this.mongoDB;
    }

    public MySQL getMySQL() {
        return this.mySQL;
    }

    public Redis getRedis() {
        return this.redis;
    }
}

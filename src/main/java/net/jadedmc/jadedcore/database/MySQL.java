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
package net.jadedmc.jadedcore.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.jadedmc.jadedcore.JadedCorePlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

/**
 * Manages the connection process to MySQL.
 */
public class MySQL {
    private final JadedCorePlugin plugin;
    private Connection connection;
    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int port;
    private HikariDataSource dataSource;

    /**
     * Loads the MySQL database connection info.
     * @param plugin Instance of the plugin.
     */
    public MySQL(@NotNull final JadedCorePlugin plugin) {
        this.plugin = plugin;
        host = plugin.getConfigManager().getConfig().getString("MySQL.host");
        database = plugin.getConfigManager().getConfig().getString("MySQL.database");
        username = plugin.getConfigManager().getConfig().getString("MySQL.username");
        password = plugin.getConfigManager().getConfig().getString("MySQL.password");
        port = plugin.getConfigManager().getConfig().getInt("MySQL.port");
    }

    public void openConnection() {
        final HikariConfig config = new HikariConfig();

        // 1. Basic Setup
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);

        // 2. Performance Tuning (Standard for Minecraft)
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // 3. Connection Pool Settings
        config.setMaximumPoolSize(30); // Adjust based on player count
        config.setMinimumIdle(2);
        config.setMaxLifetime(1800000); // 30 minutes
        config.setConnectionTimeout(5000); // 5 seconds

        this.dataSource = new HikariDataSource(config);

        // Run your table creation logic here
        createTables();
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            throw new SQLException("DataSource is closed or null!");
        }
        return dataSource.getConnection(); // This returns a fresh connection from the pool
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private void createTables() {
        final String[] queries = {
                "CREATE TABLE IF NOT EXISTS api_keys (uuid VARCHAR(36), apiKey VARCHAR(36), PRIMARY KEY(uuid, apiKey));",
                "CREATE TABLE IF NOT EXISTS staff_settings (uuid VARCHAR(36), vanish BOOLEAN DEFAULT FALSE, commandSpy BOOLEAN DEFAULT FALSE, PRIMARY KEY(uuid));",
                "CREATE TABLE IF NOT EXISTS achievements_list (id VARCHAR(36), mode VARCHAR(36), name VARCHAR(36), description VARCHAR(128), achievementPoints INT DEFAULT 0, rewards VARCHAR(256), PRIMARY KEY(id));",
                "CREATE TABLE IF NOT EXISTS player_achievements (uuid VARCHAR(36), achievementID VARCHAR(36), time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(uuid, achievementID));",
                "CREATE TABLE IF NOT EXISTS player_info (uuid VARCHAR(36), username VARCHAR(16), ip VARCHAR(36), level INT DEFAULT 1, experience INT DEFAULT 0, firstOnline TIMESTAMP DEFAULT CURRENT_TIMESTAMP, lastOnline TIMESTAMP DEFAULT CURRENT_TIMESTAMP, achievementPoints INT DEFAULT 0);"
        };

        try (final Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            for (final String sql : queries) {
                statement.execute(sql);
            }
            plugin.getLogger().info("Successfully initialized all database tables.");
        }
        catch (final SQLException exception) {
            plugin.getLogger().severe("Could not initialize database tables!");
            exception.printStackTrace();
        }
    }


    /**
     * Get the connection.
     * @return Connection
     */
    /*
    public Connection getConnection() {
        return connection;
    }

     */

    /**
     * Get if plugin is connected to the database.
     * @return Connected
     */
    /*
    private boolean isConnected() {
        return (connection != null);
    }

     */

    /**
     * Open a MySQL Connection
     */
    /*
    public void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            synchronized(JadedCorePlugin.class) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false&characterEncoding=utf8", username, password);
            }

            // Prevents losing connection to MySQL.
            plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, ()-> {
                try {
                    connection.isValid(0);
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }, 504000, 504000);

            {
                PreparedStatement api_keys = connection.prepareStatement("CREATE TABLE IF NOT EXISTS api_keys (" +
                        "uuid VARCHAR(36)," +
                        "apiKey VARCHAR(36)," +
                        "PRIMARY KEY(uuid, apiKey)" +
                        ");");
                api_keys.execute();
            }

            {
                PreparedStatement staff_settings = connection.prepareStatement("CREATE TABLE IF NOT EXISTS staff_settings (" +
                        "uuid VARCHAR(36)," +
                        "vanish BOOLEAN DEFAULT FALSE," +
                        "commandSpy BOOLEAN DEFAULT FALSE," +
                        "PRIMARY KEY(uuid)" +
                        ");");
                staff_settings.execute();
            }

            {
                PreparedStatement achievements_list = connection.prepareStatement("CREATE TABLE IF NOT EXISTS achievements_list (" +
                        "id VARCHAR(36)," +
                        "mode VARCHAR(36)," +
                        "name VARCHAR(36)," +
                        "description VARCHAR(128)," +
                        "achievementPoints INT DEFAULT 0, " +
                        "rewards VARCHAR(256)," +
                        "PRIMARY KEY(id)" +
                        ");");
                achievements_list.execute();
            }

            {
                PreparedStatement player_achievements = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_achievements (" +
                        "uuid VARCHAR(36)," +
                        "achievementID VARCHAR(36), " +
                        "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "PRIMARY KEY(uuid,achievementID)" +
                        ");");
                player_achievements.execute();
            }

            PreparedStatement player_info = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_info (" +
                    "uuid VARCHAR(36)," +
                    "username VARCHAR(16)," +
                    "ip VARCHAR(36)," +
                    "level INT DEFAULT 1," +
                    "experience INT DEFAULT 0," +
                    "firstOnline TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "lastOnline TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "achievementPoints INT DEFAULT 0" +
                    ");");
            player_info.execute();
        }
        catch(SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

     */
}
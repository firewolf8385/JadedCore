package net.jadedmc.jadedcore.settings;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedsync.api.integration.Integration;
import net.jadedmc.jadedsync.api.player.JadedSyncPlayer;
import net.jadedmc.jadedsync.api.server.CurrentInstance;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class JadedSyncIntegration extends Integration {
    private final JadedCorePlugin plugin;

    public JadedSyncIntegration(@NotNull final JadedCorePlugin plugin) {
        super("jadedcore");
        this.plugin = plugin;
    }

    @Override
    public String getPlayerIntegration(@NotNull final JadedSyncPlayer jadedSyncPlayer) {
        final Document document = new Document()
                .append("game", plugin.getConfigManager().getConfig().getString("serverGame"));

        return document.toJson();
    }

    @Override
    public String getServerIntegration(@NotNull final CurrentInstance currentInstance) {
        final Document document = new Document()
                .append("game", plugin.getConfigManager().getConfig().getString("serverGame"))
                .append("lobby", plugin.getLobbyManager().isEnabled())
                .append("devMode", plugin.getConfigManager().getConfig().getString("devMode"));
        return document.toJson();
    }
}
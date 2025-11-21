package net.jadedmc.jadedcore.lobby;

import me.clip.placeholderapi.PlaceholderAPI;
import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedutils.scoreboard.CustomScoreboard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LobbyScoreboard extends CustomScoreboard {
    private final JadedCorePlugin plugin;

    public LobbyScoreboard(@NotNull final JadedCorePlugin plugin, @NotNull final Player player) {
        super(player);
        this.plugin = plugin;
    }

    @Override
    public void update(@NotNull final Player player) {
        // Sets up the scoreboard.
        setTitle(plugin.getConfigManager().getConfig().getString("Lobby.Scoreboard.Title"));

        List<String> lines = plugin.getConfigManager().getConfig().getStringList("Lobby.Scoreboard.Lines");

        int lineNumber = 0;
        for(String line : lines) {
            setLine(lineNumber, PlaceholderAPI.setPlaceholders(player, line));

            lineNumber++;
        }

        for(int i = lineNumber; i <= 14; i++) {
            removeLine(i);
        }
    }
}
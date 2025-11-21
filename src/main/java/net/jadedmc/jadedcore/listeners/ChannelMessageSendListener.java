package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedchat.features.channels.events.ChannelMessageSendEvent;
import net.jadedmc.jadedcore.JadedCorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * Listens to the ChannelMessageSendEvent, which is called every time a player sends a message in a chat channel.
 */
public class ChannelMessageSendListener implements Listener {
    private final JadedCorePlugin plugin;

    /**
     * Creates the Listener.
     * @param plugin Instance of the plugin.
     */
    public ChannelMessageSendListener(@NotNull final JadedCorePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the ChannelMessageSendEvent is called.
     * @param event ChannelMessageSendEvent.
     */
    @EventHandler
    public void onMessageSend(ChannelMessageSendEvent event) {
        plugin.getAchievementManager().getAchievement("general_2").unlock(event.getPlayer());
    }
}
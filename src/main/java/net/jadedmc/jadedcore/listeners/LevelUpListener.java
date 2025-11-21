package net.jadedmc.jadedcore.listeners;

import net.jadedmc.jadedcore.JadedAPI;
import net.jadedmc.jadedcore.events.LevelUpEvent;
import net.jadedmc.jadedutils.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUpListener implements Listener {

    @EventHandler
    public void onLevelUp(final LevelUpEvent event) {
        final Player player = event.getPlayer();

        ChatUtils.chat(player, "<center><dark_aqua>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        ChatUtils.chat(player,"<center><dark_aqua><obf>#</obf><aqua><bold>Level Up!</bold><dark_aqua><obf>#</obf>");
        ChatUtils.chat(player, "");
        ChatUtils.chat(player, "<center>  <aqua>Your level has increased to level &f" + event.getLevel() + "&3!");
        ChatUtils.chat(player, "");
        ChatUtils.chat(player, "<center>&7You need &3" + (JadedAPI.getRequiredExp(event.getLevel()) - event.getLevelsPlayer().getExperience()) + " &7more experience to level up again.");
        ChatUtils.chat(player, "<center><dark_aqua>▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
    }
}
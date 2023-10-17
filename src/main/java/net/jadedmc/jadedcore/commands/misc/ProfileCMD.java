package net.jadedmc.jadedcore.commands.misc;

import net.jadedmc.jadedcore.JadedCore;
import net.jadedmc.jadedcore.commands.AbstractCommand;
import net.jadedmc.jadedcore.features.ProfileGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCMD extends AbstractCommand {
    private final JadedCore plugin;

    public ProfileCMD(JadedCore plugin) {
        super("profile", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new ProfileGUI(plugin, player).open(player);
    }
}
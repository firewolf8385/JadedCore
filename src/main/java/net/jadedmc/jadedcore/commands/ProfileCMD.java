package net.jadedmc.jadedcore.commands;

import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.guis.ProfileGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ProfileCMD extends AbstractCommand {
    private final JadedCorePlugin plugin;

    public ProfileCMD(@NotNull final JadedCorePlugin plugin) {
        super("profile", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull final CommandSender sender, final String[] args) {
        final Player player = (Player) sender;
        new ProfileGUI(plugin, player).open(player);
    }
}
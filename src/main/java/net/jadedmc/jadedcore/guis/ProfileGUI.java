package net.jadedmc.jadedcore.guis;

import me.clip.placeholderapi.PlaceholderAPI;
import net.jadedmc.jadedcore.JadedAPI;
import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.achievements.Achievement;
import net.jadedmc.jadedcore.player.JadedPlayer;
import net.jadedmc.jadedutils.gui.CustomGUI;
import net.jadedmc.jadedutils.items.ItemBuilder;
import net.jadedmc.jadedutils.items.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

/**
 * Runs the profile GUI, which displays player data.
 */
public class ProfileGUI extends CustomGUI {

    /**
     * Creates the GUI
     * @param plugin Instance of the plugin.
     * @param player Player the GUI is for.
     */
    public ProfileGUI(JadedCorePlugin plugin, Player player) {
        super(54, "Profile");
        JadedPlayer jadedPlayer = plugin.getJadedPlayerManager().getPlayer(player);

        addFiller(0,1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String firstJoined = dateFormat.format(jadedPlayer.getFirstJoined());

        ItemStack characterInfo = new SkullBuilder().fromPlayer(player).asItemBuilder()
                .setDisplayName("<green><bold>Character Info")
                .addLore("<gray>Player: <green>" + player.getName())
                .addLore("<gray>Rank: " + jadedPlayer.getRank().getDisplayName())
                .addLore("")
                .addLore("<gray>Level: <green>" + jadedPlayer.getLevel())
                .addLore("<gray>Experience: <green>" + jadedPlayer.getExperience())
                .addLore("")
                .addLore("<gray>First Joined: <green>" + firstJoined)
                .build();
        setItem(22, characterInfo);

        int achievementPoints = 0;
        for(final Achievement achievement : jadedPlayer.getAchievements()) {
            achievementPoints += achievement.getPoints();
        }

        ItemStack achievements = new ItemBuilder(Material.DIAMOND)
                .setDisplayName("<green><bold>Achievements")
                .addLore("<gray>Unlocked: <green>" + jadedPlayer.getAchievements().size())
                .addLore("<gray>Achievement Points: <yellow>" + achievementPoints)
                .build();
        setItem(31, achievements, (p, a) -> new AchievementsGUI(plugin, p).open(p));

        final ItemStack leveling = new ItemBuilder(Material.BREWING_STAND)
                .setDisplayName("<green><bold>Leveling")
                .addLore("")
                .addLore("<dark_aqua>Level " + jadedPlayer.getLevel() + " " + JadedAPI.getLevelBar(jadedPlayer.getExperience(), jadedPlayer.getLevel()) + " <dark_aqua>Level " + (jadedPlayer.getLevel() + 1))
                .addLore("")
                .addLore(PlaceholderAPI.setPlaceholders(player, "<gray>Remaining Experience: <dark_aqua>" + (JadedAPI.getRequiredExp(jadedPlayer.getLevel()) - jadedPlayer.getExperience())))
                .build();
        setItem(32, leveling);
    }
}
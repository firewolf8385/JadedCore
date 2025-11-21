package net.jadedmc.jadedcore.guis;

import net.jadedmc.jadedcore.JadedAPI;
import net.jadedmc.jadedcore.JadedCorePlugin;
import net.jadedmc.jadedcore.minigames.Minigame;
import net.jadedmc.jadedutils.gui.CustomGUI;
import net.jadedmc.jadedutils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

public class GamesGUI extends CustomGUI {

    public GamesGUI(@NotNull final JadedCorePlugin plugin) {
        super(54, "Games");
        addFiller(0,1,2,3,4,5,6,7,8,45,46,47,48,49,50,51,52,53);

        ItemBuilder cactusRush = new ItemBuilder(Material.CACTUS)
                .setDisplayName("<green><bold>Cactus Rush")
                .addLore("<dark_gray>Competitive")
                .addLore("")
                .addLore("<gray>Team-Based Cactus")
                .addLore("<gray>Fighting Minigame.")
                .addLore("")
                .addLore("<green>▸ Click to Connect")
                .addLore("<gray>Join " + plugin.getMinigameManager().getPlayerCount(Minigame.CACTUS_RUSH) + " others playing!")
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_UNBREAKABLE);
        setItem(20, cactusRush.build(), (p,a) -> JadedAPI.sendToLobby(p, Minigame.CACTUS_RUSH));


        ItemBuilder duels = new ItemBuilder(Material.NETHERITE_SWORD)
                .setDisplayName("<green><bold>Duels")
                .addLore("<dark_gray>Competitive")
                .addLore("")
                .addLore("<gray>Arena-based pvp duels")
                .addLore("<gray>with a variety of kits!")
                .addLore("")
                .addLore("<green>▸ Click to Connect")
                .addLore("<gray>Join " + plugin.getMinigameManager().getPlayerCount(Minigame.DUELS) + " others playing!")
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_UNBREAKABLE);

        setItem(22, duels.build(), (p, a) -> JadedAPI.sendToLobby(p, Minigame.DUELS));

        ItemBuilder elytraPvP = new ItemBuilder(Material.ELYTRA)
                .setDisplayName("<green><bold>ElytraPvP")
                .addLore("<dark_gray>Persistent Game")
                .addLore("")
                .addLore("<gray>Action-Packed pvp in the")
                .addLore("<gray>air using bows!")
                .addLore("")
                .addLore("<green>▸ Click to Connect")
                .addLore("<gray>Join " + plugin.getMinigameManager().getPlayerCount(Minigame.ELYTRAPVP) + " others playing!")
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_UNBREAKABLE);
        setItem(24, elytraPvP.build(), (p,a) -> JadedAPI.sendToLobby(p, Minigame.ELYTRAPVP));

        ItemBuilder lobby = new ItemBuilder(Material.BOOKSHELF)
                .setDisplayName("<green><bold>Main Lobby")
                .addLore("")
                .addLore("<green>▸ Click to Connect")
                .addLore("<gray>Join " + plugin.getMinigameManager().getPlayerCount(Minigame.HUB) + " others playing!")
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_UNBREAKABLE);
        setItem(30, lobby.build(), (p,a) -> JadedAPI.sendToLobby(p, Minigame.HUB));

        /*
        ItemBuilder limbo = new ItemBuilder(Material.BEDROCK)
                .setDisplayName("<green><bold>Limbo")
                .addLore("")
                .addLore("<green>▸ Click to Connect")
                .addLore("<gray>Join " + JadedAPI.getInstanceMonitor().getPlayerCount(Minigame.LIMBO) + " others playing!")
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_UNBREAKABLE);
        setItem(31, limbo.build(), (p,a) -> JadedAPI.sendBungeecordMessage(p, "BungeeCord", "Connect", "limbo"));

         */

        ItemBuilder tournaments = new ItemBuilder(Material.GOLD_INGOT)
                .setDisplayName("<green><bold>Tournament Lobby")
                .addLore("")
                .addLore("<green>▸ Click to Connect")
                .addLore("<gray>Join " + plugin.getMinigameManager().getPlayerCount(Minigame.TOURNAMENTS) + " others playing!")
                .setUnbreakable(true)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_UNBREAKABLE);
        setItem(32, tournaments.build(), (p,a) -> JadedAPI.sendToLobby(p, Minigame.TOURNAMENTS));
    }
}
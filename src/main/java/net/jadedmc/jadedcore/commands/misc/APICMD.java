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
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package net.jadedmc.jadedcore.commands.misc;

import net.jadedmc.jadedcore.JadedCore;
import net.jadedmc.jadedutils.chat.ChatUtils;
import net.jadedmc.jadedcore.commands.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class APICMD extends AbstractCommand {
    private final JadedCore plugin;

    public APICMD(JadedCore plugin) {
        super("api", "", false);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID key = UUID.randomUUID();

        ChatUtils.chat(player, "&aYour new API key is: &f" + key.toString());

        try {
            PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("REPLACE INTO api_keys (uuid, apiKey) VALUES (?,?)");
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, key.toString());
            statement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

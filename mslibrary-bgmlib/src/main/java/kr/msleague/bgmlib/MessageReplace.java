package kr.msleague.bgmlib;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageReplace {
    public static void sex(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}

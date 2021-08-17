package kr.msleague.mslibrary.commands;

import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.PaperCommandManager;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MSCommands {

    public static PaperCommandManager paper(JavaPlugin plugin){
        return new PaperCommandManager(plugin);
    }

    public static BungeeCommandManager bungee(Plugin plugin){
        return new BungeeCommandManager(plugin);
    }
}

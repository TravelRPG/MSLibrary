package kr.msleague.bootstrap;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;


public abstract class MSPlugin {
    @Getter
    @Setter
    private static Server server;
    @Getter
    @Setter
    private static Logger logger;
    @Getter
    @Setter
    private static File dataFolder;
    @Getter
    @Setter
    private static FileConfiguration config;
    @Getter
    @Setter
    private static JavaPlugin plugin;
    public void onEnable(){

    }
    public void onDisable(){

    }
    public void saveResource(String resourcePath, boolean a){
        plugin.saveResource(resourcePath, a);
    }
}

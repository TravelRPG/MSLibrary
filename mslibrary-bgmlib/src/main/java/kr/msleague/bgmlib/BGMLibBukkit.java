package kr.msleague.bgmlib;

import kr.msleague.bootstrap.MSPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BGMLibBukkit extends MSPlugin {
    public static JavaPlugin main;
    @Override
    public void onEnable(){
        main = getPlugin();
        //Plugin start logic
    }
    @Override
    public void onDisable(){

    }
}

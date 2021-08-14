package kr.msleague.all;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.HashSet;
import java.util.Set;

public final class MSLibraryBukkitBootstrap extends JavaPlugin {
    private final Set<MSPlugin> plugins = new HashSet<>();
    @Getter
    private static MSLibraryBukkitBootstrap plugin;
    @Override
    public void onEnable(){
        MSPlugin.setServer(getServer());
        MSPlugin.setLogger(getLogger());
        MSPlugin.setDataFolder(getDataFolder());
        MSPlugin.setConfig(getConfig());
        MSPlugin.setPlugin(this);
        plugin = this;
        loadClasses();
    }
    @Override
    public void onDisable(){
        getLogger().info("Shutting down MSLibrary");
        for (MSPlugin plugin : plugins) {
            plugin.onDisable();
            getLogger().info(String.format("Plugin %s Successfully disabled!", plugin.getClass().getName()));
        }
    }
    private final void loadClasses(){
        try{
            Reflections.class.getDeclaredField("log").set(null, null);
            Reflections rf = new Reflections(ClasspathHelper.forPackage("kr.msleague"));
            Set<Class<? extends MSPlugin>> sets = rf.getSubTypesOf(MSPlugin.class);
            for (Class<? extends MSPlugin> pluginClass : sets) {
                try{
                    MSPlugin plugin = pluginClass.newInstance();
                    getLogger().info(String.format("Start initiating Plugin %s!", pluginClass.getName()));
                    plugin.onEnable();
                    plugins.add(plugin);
                    getLogger().info(String.format("Plugin %s Successfully loaded!", pluginClass.getName()));
                }catch(Exception ex){
                    System.err.println("Error While loading plugin : " + pluginClass.getName());
                    ex.printStackTrace();
                }
            }
        }catch(Throwable ex){

        }
    }

}

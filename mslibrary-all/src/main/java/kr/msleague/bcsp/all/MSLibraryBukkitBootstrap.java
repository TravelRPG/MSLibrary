package kr.msleague.bcsp.all;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.HashSet;
import java.util.Set;

public final class MSLibraryBukkitBootstrap extends JavaPlugin {
    private final Set<JavaPlugin> plugins = new HashSet<>();
    @Override
    public void onEnable(){
        loadClasses();
    }
    @Override
    public void onDisable(){
        getLogger().info("Shutting down MSLibrary");
        for (JavaPlugin plugin : plugins) {
            plugin.onDisable();
            getLogger().info(String.format("Plugin %s Successfully disabled!", plugin.getClass().getName()));
        }
    }
    private final void loadClasses(){
        Reflections rf = new Reflections(ClasspathHelper.forPackage("kr.msleague"));
        Set<Class<? extends JavaPlugin>> sets = rf.getSubTypesOf(JavaPlugin.class);
        for (Class<? extends JavaPlugin> pluginClass : sets) {
            try{
                JavaPlugin plugin = pluginClass.newInstance();
                plugin.onEnable();
                plugins.add(plugin);
                getLogger().info(String.format("Plugin %s Successfully loaded!", pluginClass.getName()));
            }catch(Exception ex){
                System.err.println("Error While loading plugin : " + pluginClass.getName());
            }
        }
    }

}

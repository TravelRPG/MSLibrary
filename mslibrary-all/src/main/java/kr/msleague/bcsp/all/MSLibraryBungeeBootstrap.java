package kr.msleague.bcsp.all;

import net.md_5.bungee.api.plugin.Plugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.HashSet;
import java.util.Set;

public final class MSLibraryBungeeBootstrap extends Plugin {
    private final Set<Plugin> plugins = new HashSet<>();
    @Override
    public void onEnable(){
        loadClasses();
    }
    @Override
    public void onDisable(){
        getLogger().info("Shutting down MSLibrary");
        for (Plugin plugin : plugins) {
            plugin.onDisable();
            getLogger().info(String.format("Plugin %s Successfully disabled!", plugin.getClass().getName()));
        }
    }
    private final void loadClasses(){
        Reflections rf = new Reflections(ClasspathHelper.forPackage("kr.msleague"));
        Set<Class<? extends Plugin>> sets = rf.getSubTypesOf(Plugin.class);
        for (Class<? extends Plugin> pluginClass : sets) {
            try{
                Plugin plugin = pluginClass.newInstance();
                plugin.onEnable();
                plugins.add(plugin);
                getLogger().info(String.format("Plugin %s Successfully loaded!", pluginClass.getName()));
            }catch(Exception ex){
                System.err.println("Error While loading plugin : " + pluginClass.getName());
            }
        }
    }
}

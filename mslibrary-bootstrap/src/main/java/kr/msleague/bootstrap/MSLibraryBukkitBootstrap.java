package kr.msleague.bootstrap;

import com.google.common.collect.Sets;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.*;

public final class MSLibraryBukkitBootstrap extends JavaPlugin {
    @Getter
    private static MSLibraryBukkitBootstrap plugin;
    private final Set<MSPlugin> plugins = new HashSet<>();

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        logo();
        getLogger().info("MSLibrary BootUp Sequence started..");
        MSPlugin.setServer(getServer());
        MSPlugin.setLogger(getLogger());
        MSPlugin.setDataFolder(getDataFolder());
        MSPlugin.setConfig(getConfig());
        MSPlugin.setPlugin(this);
        plugin = this;
        loadClasses();
        getLogger().info("MSLibrary successfully bootup! eslaped time: " + (System.currentTimeMillis() - time) + "ms");
        getLogger().info("==========================================================================================");
    }

    private final void logo() {
        getLogger().info("==========================================================================================");
        getLogger().info(" ");
        getLogger().info(" /$$      /$$  /$$$$$$  /$$       /$$ /$$                                             ");
        getLogger().info("| $$$    /$$$ /$$__  $$| $$      |__/| $$                                             ");
        getLogger().info("| $$$$  /$$$$| $$  \\__/| $$       /$$| $$$$$$$   /$$$$$$  /$$$$$$   /$$$$$$  /$$   /$$");
        getLogger().info("| $$ $$/$$ $$|  $$$$$$ | $$      | $$| $$__  $$ /$$__  $$|____  $$ /$$__  $$| $$  | $$");
        getLogger().info("| $$  $$$| $$ \\____  $$| $$      | $$| $$  \\ $$| $$  \\__/ /$$$$$$$| $$  \\__/| $$  | $$");
        getLogger().info("| $$\\  $ | $$ /$$  \\ $$| $$      | $$| $$  | $$| $$      /$$__  $$| $$      | $$  | $$");
        getLogger().info("| $$ \\/  | $$|  $$$$$$/| $$$$$$$$| $$| $$$$$$$/| $$     |  $$$$$$$| $$      |  $$$$$$$");
        getLogger().info("|__/     |__/ \\______/ |________/|__/|_______/ |__/      \\_______/|__/       \\____  $$");
        getLogger().info("                                                                             /$$  | $$");
        getLogger().info("                                                                            |  $$$$$$/");
        getLogger().info("                                                                             \\______/ ");
        getLogger().info(" ");
        getLogger().info("==========================================================================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down MSLibrary");
        for (MSPlugin plugin : plugins) {
            plugin.onDisable();
            getLogger().info(String.format("Plugin %s Successfully disabled!", plugin.getClass().getName()));
        }
    }

    private final void loadClasses() {
        getLogger().info("Started loadup plugin & modules...");
        try {
            Reflections.class.getDeclaredField("log").set(null, null);
            Reflections rf = new Reflections(ClasspathHelper.forPackage("kr.msleague"));

            Set<Class<? extends MSPlugin>> clazzes = rf.getSubTypesOf(MSPlugin.class);
            getLogger().info("System found total " + clazzes.size() + " modules.");
            getLogger().info("Calculating modules priority order..");
            Set<Class<?>> hasAnnotation = rf.getTypesAnnotatedWith(LoadPriority.class);
            Set<Class<? extends MSPlugin>> x = Sets.intersection(clazzes, hasAnnotation);
            HashSet<Class<? extends MSPlugin>> clo = new HashSet<>(clazzes);
            clo.removeAll(x);
            clazzes = clo;

            TreeMap<Integer, List<Class<? extends MSPlugin>>> map = new TreeMap<>();
            map.computeIfAbsent(0, xa -> new ArrayList<>()).addAll(clazzes);
            for (Class<? extends MSPlugin> annoClass : x) {
                int priority = annoClass.getAnnotation(LoadPriority.class).priority();
                map.computeIfAbsent(priority, xe -> new ArrayList<>()).add(annoClass);
            }

            map.forEach((priority, pluginClassList) -> {
                pluginClassList.forEach(pluginClass -> {
                    try {
                        MSPlugin plugin = pluginClass.newInstance();
                        getLogger().info(String.format("Start initiating Plugin %s!", pluginClass.getName()));
                        plugin.onEnable();
                        plugins.add(plugin);
                        getLogger().info(String.format("Plugin %s Successfully loaded with priority %d!", pluginClass.getName(), priority));
                    } catch (Exception ex) {
                        System.err.println("Error While loading plugin : " + pluginClass.getName());
                        ex.printStackTrace();
                    }
                });
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

}

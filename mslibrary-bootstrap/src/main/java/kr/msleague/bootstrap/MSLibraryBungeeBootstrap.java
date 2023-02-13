package kr.msleague.bootstrap;

import com.google.common.collect.Sets;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import net.md_5.bungee.api.plugin.Plugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.lang.reflect.Field;
import java.util.*;

public final class MSLibraryBungeeBootstrap extends Plugin {
    private final Set<Plugin> plugins = new HashSet<>();

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        logo();
        getLogger().info("MSLibrary BootUp Sequence started..");
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
        for (Plugin plugin : plugins) {
            plugin.onDisable();
            getLogger().info(String.format("Plugin %s Successfully disabled!", plugin.getClass().getName()));
        }
    }

    private final void loadClasses() {
        getLogger().info("Started loadup plugin & modules...");
        try {
            //Reflections.class.getDeclaredField("log").set(null, null);
            Reflections rf = new Reflections(ClasspathHelper.forPackage("kr.msleague"));

            //Classes which extends Plugin.class
            Set<Class<? extends Plugin>> clazzes = rf.getSubTypesOf(Plugin.class);
            getLogger().info("System found total " + clazzes.size() + " modules.");
            getLogger().info("Calculating modules priority order..");
            //Classes which annotated with LoadPriority.class
            Set<Class<?>> hasAnnotation = rf.getTypesAnnotatedWith(LoadPriority.class);
            Set<Class<? extends Plugin>> x = Sets.intersection(clazzes, hasAnnotation);
            HashSet<Class<? extends Plugin>> clo = new HashSet<>(clazzes);
            clo.removeAll(x);
            clazzes = clo;

            TreeMap<Integer, List<Class<? extends Plugin>>> map = new TreeMap<>();
            map.computeIfAbsent(0, xa -> new ArrayList<>()).addAll(clazzes);
            for (Class<? extends Plugin> annoClass : x) {
                int priority = annoClass.getAnnotation(LoadPriority.class).priority();
                map.computeIfAbsent(priority, xe -> new ArrayList<>()).add(annoClass);
            }

            map.forEach((priority, pluginClassList) -> {
                pluginClassList.forEach(pluginClass -> {
                    if (pluginClass.equals(getClass()))
                        return;
                    try {
                        ClassLoader loader = pluginClass.getClassLoader();
                        Class<?> classLoader = Class.forName("net.md_5.bungee.api.plugin.PluginClassloader");
                        Field f = classLoader.getDeclaredField("plugin");
                        f.setAccessible(true);
                        f.set(loader, null);

                        //Dependency injection to use Plugin class as active
                        Plugin plugin = pluginClass.newInstance();
                        f = Plugin.class.getDeclaredField("description");
                        f.setAccessible(true);
                        f.set(plugin, getDescription());
                        f = Plugin.class.getDeclaredField("proxy");
                        f.setAccessible(true);
                        f.set(plugin, getProxy());
                        f = Plugin.class.getDeclaredField("file");
                        f.setAccessible(true);
                        f.set(plugin, getDataFolder());
                        f = Plugin.class.getDeclaredField("logger");
                        f.setAccessible(true);
                        f.set(plugin, getLogger());
                        f = Plugin.class.getDeclaredField("service");
                        f.setAccessible(true);
                        f.set(plugin, getExecutorService());

                        plugin.onEnable();
                        plugins.add(plugin);
                        getLogger().info(String.format("Plugin %s Successfully loaded with priority %d!", pluginClass.getName(), priority));
                    } catch (Exception ex) {
                        System.err.println("Error While loading plugin : " + pluginClass.getName());
                        ex.printStackTrace();
                    }
                });
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

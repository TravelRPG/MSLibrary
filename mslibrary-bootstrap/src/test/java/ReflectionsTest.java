import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.Set;

public class ReflectionsTest {
    public static void main(String[] args){
        Reflections rf = new Reflections(ClasspathHelper.forPackage("kr.msleague"));
        Set<Class<? extends JavaPlugin>> sets = rf.getSubTypesOf(JavaPlugin.class);
        for (Class<? extends JavaPlugin> set : sets) {
            System.out.println(set.getName());
        }
    }
}

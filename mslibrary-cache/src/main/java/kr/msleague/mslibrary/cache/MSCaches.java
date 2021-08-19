package kr.msleague.mslibrary.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import kr.msleague.bootstrap.MSPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

//https://github.com/ben-manes/caffeine/wiki
public class MSCaches extends MSPlugin {

    private static HashMap<String, LoadingCache<?, ?>> caches = new HashMap<>();
    private static HashMap<String, AsyncLoadingCache<?, ?>> asyncCaches = new HashMap<>();

    public static LoadingCache<?, ?> sync(String name){
        return caches.get(name);
    }

    public static AsyncLoadingCache<?, ?> async(String name){
        return asyncCaches.get(name);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> LoadingCache<K, V> sync(String name, Class<K> clazz, Class<V> clazz2){
        return (LoadingCache<K, V>) caches.get(name);
    }

    @SuppressWarnings("unchecked")
    public static <K, V> AsyncLoadingCache<K, V> async(String name, Class<K> clazz, Class<V> clazz2){
        return (AsyncLoadingCache<K, V>) asyncCaches.get(name);
    }

    public static void addCache(String name, LoadingCache<?, ?> cache){
        if(!caches.containsKey(name)){
            caches.put(name, cache);
        }
    }

    public static void addCache(String name, AsyncLoadingCache<?, ?> cache){
        if(!asyncCaches.containsKey(name)){
            asyncCaches.put(name, cache);
        }
    }


    @SuppressWarnings("unchecked")
    public static ItemStack getHead(UUID uuid){
        LoadingCache<UUID, ItemStack> headCache = ((LoadingCache<UUID, ItemStack>) caches.get("player-head"));
        if(headCache != null) {
            ItemStack item = headCache.get(uuid);
            if(item != null)
                return item;
        }
        return null;
    }

    @Override
    public void onEnable() {
        LoadingCache<UUID, ItemStack> headCache = Caffeine.newBuilder().build(uuid -> {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
            SkullMeta meta = ((SkullMeta) item.getItemMeta());
            //todo: change logic to set custom nbt tag;
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
            return item;
        });
        caches.put("player-head", headCache);
    }
}

package kr.msleague.mslibrary.cache.api;


import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface MSLoadingCache<K, V> extends MSCache<K, V>{

    @Nullable
    V get(K var1);

    Map<K, V> getAll(Iterable<? extends K> var1);

    CompletableFuture<V> refresh(K var1);

    CompletableFuture<Map<K, V>> refreshAll(Iterable<? extends K> var1);
}

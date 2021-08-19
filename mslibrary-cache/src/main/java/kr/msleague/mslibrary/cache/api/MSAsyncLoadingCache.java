package kr.msleague.mslibrary.cache.api;

import com.github.benmanes.caffeine.cache.AsyncCache;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface MSAsyncLoadingCache<K, V> extends MSAsyncCache<K, V> {

    CompletableFuture<V> get(K var1);

    CompletableFuture<Map<K, V>> getAll(Iterable<? extends K> var1);
}

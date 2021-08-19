package kr.msleague.mslibrary.cache.api;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface MSCache<K, V> {

    @Nullable
    V getIfPresent(K var1);

    @Nullable
    V get(K var1, Function<? super K, ? extends V> var2);

    Map<K, V> getAllPresent(Iterable<? extends K> var1);

    Map<K, V> getAll(Iterable<? extends K> var1, Function<? super Set<? extends K>, ? extends Map<? extends K, ? extends V>> var2);

    void put(K var1, V var2);

    void putAll(Map<? extends K, ? extends V> var1);

    void invalidate(K var1);

    void invalidateAll(Iterable<? extends K> var1);

    void invalidateAll();
}

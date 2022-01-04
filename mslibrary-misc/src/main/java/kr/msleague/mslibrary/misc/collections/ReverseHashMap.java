package kr.msleague.mslibrary.misc.collections;

import java.util.HashMap;
import java.util.function.Function;

public class ReverseHashMap<K, V> extends HashMap<K, V> {
    private HashMap<V, K> reversedMap = new HashMap<>();

    @Override
    public V put(K key, V value) {
        reversedMap.put(value, key);
        return super.put(key, value);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        reversedMap.putIfAbsent(value, key);
        return super.putIfAbsent(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
       // reversedMap.computeIfAbsent(value, key);
        return super.computeIfAbsent(key, mappingFunction);
    }

    public HashMap<V,K> reverse(){
        return reversedMap;
    }
}

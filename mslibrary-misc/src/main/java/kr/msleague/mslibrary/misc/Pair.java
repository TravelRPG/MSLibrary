package kr.msleague.mslibrary.misc;

import java.util.Objects;

public class Pair<K, V> {
    private K k;
    private V v;

    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getKey() {
        return k;
    }

    public V getValue() {
        return v;
    }

    @Override
    public int hashCode() {
        return Objects.hash(k, v);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pair && ((Pair) obj).k.equals(k) && ((Pair) obj).v.equals(v);
    }
}

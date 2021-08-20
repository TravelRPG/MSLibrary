package kr.msleague.mslibrary.misc;

import java.util.Objects;

public class Triple<A, B, C> {
    private final A a;
    private final B b;
    private final C c;
    public Triple(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public A getKey(){
        return a;
    }
    public B getMiddle(){
        return b;
    }
    public C getValue(){
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> Triple = (Triple<?, ?, ?>) o;
        return Objects.equals(a, Triple.a) && Objects.equals(b, Triple.b) && Objects.equals(c, Triple.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}

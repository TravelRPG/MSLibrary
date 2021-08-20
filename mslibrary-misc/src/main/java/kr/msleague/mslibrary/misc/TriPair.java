package kr.msleague.mslibrary.misc;

import java.util.Objects;

public class TriPair<A, B, C> {
    private final A a;
    private final B b;
    private final C c;
    public TriPair(A a, B b, C c){
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
        TriPair<?, ?, ?> triPair = (TriPair<?, ?, ?>) o;
        return Objects.equals(a, triPair.a) && Objects.equals(b, triPair.b) && Objects.equals(c, triPair.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}

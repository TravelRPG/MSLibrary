package kr.msleague.mslibrary.database.api;

import java.util.function.Function;

public interface ThrowingFunction<T, R> extends Function<T, R> {

    @Override
    default R apply(T t) {
        try {
            return acceptThrowing(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    R acceptThrowing(T t) throws Exception;
}

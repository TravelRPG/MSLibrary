package kr.msleague.mslibrary.database.api;

import java.util.function.Consumer;

public interface ThrowingConsumer<T> extends Consumer<T> {

    @Override
    default void accept(T t) {
        try {
            acceptThrowing(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void acceptThrowing(T t) throws Exception;
}

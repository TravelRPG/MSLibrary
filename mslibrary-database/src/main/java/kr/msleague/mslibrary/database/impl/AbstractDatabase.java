package kr.msleague.mslibrary.database.impl;

import kr.msleague.mslibrary.database.api.MSDatabase;
import kr.msleague.mslibrary.misc.ThrowingFunction;

import java.util.concurrent.Future;

public abstract class AbstractDatabase<T> implements MSDatabase<T> {
    @Override
    public <R> Future<R> executeAsync(ThrowingFunction<T, R> function) {
        return null;
    }

    @Override
    public <R> R execute(ThrowingFunction<T, R> function) {
        return null;
    }
}

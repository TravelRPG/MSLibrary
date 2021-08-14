package kr.msleague.mslibrary.database.impl.internal;

import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.api.MSDatabase;
import kr.msleague.mslibrary.misc.ThrowingFunction;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class MongoDatabase implements MSDatabase<com.mongodb.client.MongoDatabase> {

    final ExecutorService service;
    com.mongodb.client.MongoDatabase handle;

    @Override
    public boolean connect(DatabaseConfig config) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <R> Future<R> executeAsync(ThrowingFunction<com.mongodb.client.MongoDatabase, R> function) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <R> R execute(ThrowingFunction<com.mongodb.client.MongoDatabase, R> function) {
        throw new UnsupportedOperationException("not implemented");
    }
}

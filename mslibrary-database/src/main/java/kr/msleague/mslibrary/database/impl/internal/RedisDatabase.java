package kr.msleague.mslibrary.database.impl.internal;

import io.lettuce.core.api.sync.RedisCommands;
import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.api.MSDatabase;
import kr.msleague.mslibrary.misc.ThrowingFunction;

import java.util.concurrent.Future;

public class RedisDatabase implements MSDatabase<RedisCommands<String, String>> {

    RedisCommands<String, String> syncCommands;

    @Override
    public boolean connect(DatabaseConfig config) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <R> Future<R> executeAsync(ThrowingFunction<RedisCommands<String, String>, R> function) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public <R> R execute(ThrowingFunction<RedisCommands<String, String>, R> function) {
        throw new UnsupportedOperationException("not implemented");
        /*
        try {
            return function.acceptThrowing(syncCommands);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
         */
    }

}

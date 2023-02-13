package kr.msleague.mslibrary.database.impl.internal;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.api.MSDatabase;
import kr.msleague.mslibrary.database.api.ThrowingConsumer;
import kr.msleague.mslibrary.database.api.ThrowingFunction;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class MySQLDatabase implements MSDatabase<Connection> {

    protected final ExecutorService service;
    HikariDataSource dataSource;

    public void shutdown(){
        dataSource.close();
    }

    @Override
    public boolean connect(DatabaseConfig config) {
        try {
            HikariConfig hikariConfig = new HikariConfig();
            String address = config.getAddress();
            String port = config.getPort();
            String database = config.getDatabase();
            String username = config.getUser();
            String password = config.getPassword();
            String autoReconnect = config.get("autoReconnect");
            String allowMultiQueries = config.get("allowMultiQueries");

            hikariConfig.setJdbcUrl("jdbc:mysql://" + address + ":" + port + "/" + database + "?autoReconnect=" + autoReconnect + "&allowMultiQueries=" + allowMultiQueries);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.addDataSourceProperty("useSSL", config.get("useSSL", "true"));
            hikariConfig.addDataSourceProperty("maximumPoolSize", config.get("maximumPoolSize", "10"));
            hikariConfig.addDataSourceProperty("cachePrepStmts", config.get("cachePrepStmts", "true"));
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", config.get("prepStmtCacheSize", "250"));
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", config.get("prepStmtCacheSqlLimit", "2048"));
            hikariConfig.addDataSourceProperty("useServerPrepStmts", config.get("useServerPrepStmts", "true"));
            hikariConfig.addDataSourceProperty("useLocalSessionState", config.get("useLocalSessionState", "true"));
            hikariConfig.addDataSourceProperty("rewriteBatchedStatements", config.get("rewriteBatchedStatements", "true"));
            hikariConfig.addDataSourceProperty("cacheResultSetMetadata", config.get("cacheResultSetMetadata", "true"));
            hikariConfig.addDataSourceProperty("cacheServerConfiguration", config.get("cacheServerConfiguration", "true"));
            hikariConfig.addDataSourceProperty("elideSetAutoCommits", config.get("elideSetAutoCommits", "true"));
            hikariConfig.addDataSourceProperty("maintainTimeStats", config.get("maintainTimeStats", "false"));
            hikariConfig.addDataSourceProperty("characterEncoding", config.get("characterEncoding", "utf8"));
            hikariConfig.addDataSourceProperty("useUnicode", config.get("useUnicode", "true"));
            dataSource = new HikariDataSource(hikariConfig);
            try (Connection con = dataSource.getConnection()) {
                con.prepareStatement("SELECT 1").execute();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <R> CompletableFuture<R> executeAsync(ThrowingFunction<Connection, R> function) {
        CompletableFuture<R> future = new CompletableFuture<>();
        service.submit(() -> {
            try {
                try (Connection con = dataSource.getConnection()) {
                    future.complete(function.acceptThrowing(con));
                }
            }catch (Throwable e){
                future.completeExceptionally(e);
            }
            return null;
        });
        return future;
    }

    @Override
    public void executeAsync(ThrowingConsumer<Connection> consumer) {
        service.submit(() -> {
            try (Connection connection = dataSource.getConnection()) {
                consumer.acceptThrowing(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public <R> R execute(ThrowingFunction<Connection, R> function) {
        try (Connection con = dataSource.getConnection()) {
            return function.acceptThrowing(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void execute(ThrowingConsumer<Connection> consumer) {
        try (Connection con = dataSource.getConnection()) {
            consumer.acceptThrowing(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

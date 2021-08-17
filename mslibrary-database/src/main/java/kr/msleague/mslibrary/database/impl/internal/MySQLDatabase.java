package kr.msleague.mslibrary.database.impl.internal;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.api.MSDatabase;
import kr.msleague.mslibrary.misc.ThrowingConsumer;
import kr.msleague.mslibrary.misc.ThrowingFunction;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class MySQLDatabase implements MSDatabase<Connection> {

    final ExecutorService service;
    HikariDataSource dataSource;

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

            hikariConfig.setJdbcUrl("jdbc:mysql://" + address + ":" + port + "/" + database + "?autoReconnect="+autoReconnect+"&allowMultiQueries="+allowMultiQueries);
            hikariConfig.setUsername(username);
            hikariConfig.setPassword(password);
            hikariConfig.addDataSourceProperty("maximumPoolSize", config.get("maximumPoolSize", "10"));
            hikariConfig.addDataSourceProperty("cachePrepStmts", config.get("cachePrepStmts","true"));
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", config.get("prepStmtCacheSize", "250"));
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", config.get("prepStmtCacheSqlLimit", "2048"));
            hikariConfig.addDataSourceProperty("useServerPrepStmts", config.get("useServerPrepStmts", "true"));
            hikariConfig.addDataSourceProperty("useLocalSessionState", config.get("useLocalSessionState", "true"));
            hikariConfig.addDataSourceProperty("rewriteBatchedStatements", config.get("rewriteBatchedStatements", "true"));
            hikariConfig.addDataSourceProperty("cacheResultSetMetadata", config.get("cacheResultSetMetadata", "true"));
            hikariConfig.addDataSourceProperty("cacheServerConfiguration", config.get("cacheServerConfiguration", "true"));
            hikariConfig.addDataSourceProperty("elideSetAutoCommits", config.get("elideSetAutoCommits", "true"));
            hikariConfig.addDataSourceProperty("maintainTimeStats", config.get("maintainTimeStats", "false"));
            dataSource = (HikariDataSource) hikariConfig.getDataSource();
            try(Connection con = dataSource.getConnection()){
                con.prepareStatement("SELECT 1").execute();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <R> Future<R> executeAsync(ThrowingFunction<Connection, R> function) {
        return service.submit(()->{
            try (Connection con = dataSource.getConnection()){
                return function.acceptThrowing(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void executeAsync(ThrowingConsumer<Connection> consumer) {
        service.submit(()-> {
            try (Connection connection = dataSource.getConnection()){
                consumer.acceptThrowing(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public <R> R execute(ThrowingFunction<Connection, R> function) {
        try (Connection con = dataSource.getConnection()){
            return function.acceptThrowing(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void execute(ThrowingConsumer<Connection> consumer) {
        try (Connection con = dataSource.getConnection()){
            consumer.acceptThrowing(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

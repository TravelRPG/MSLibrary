package kr.msleague.mslibrary.database.impl.internal;

import com.zaxxer.hikari.HikariConfig;
import kr.msleague.mslibrary.database.impl.HashDatabaseConfig;

public class DefaultHikariConfig extends HashDatabaseConfig {
    public DefaultHikariConfig() {
        this.setManually("maximumPoolSize", "10");
        this.setManually("autoReconnect", "true");
        this.setManually("cachePrepStmts", "true");
        this.setManually("prepStmtCacheSize", "250");
        this.setManually("prepStmtCacheSqlLimit", "2048");
        this.setManually("useServerPrepStmts", "true");
        this.setManually("useLocalSessionState", "true");
        this.setManually("rewriteBatchedStatements", "true");
        this.setManually("cacheResultSetMetadata", "true");
        this.setManually("cacheServerConfiguration", "true");
        this.setManually("elideSetAutoCommits", "true");
        this.setManually("maintainTimeStats", "false");
        this.setManually("allowMultiQueries", "true");
    }
}

package kr.msleague.database;


import kr.msleague.mslibrary.database.MSLDatabases;
import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.impl.internal.MySQLDatabase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.concurrent.Executors;

public class DatabaseTest {


    @Test
    @Ignore
    public void mysqlTest(){
        DatabaseConfig config = MSLDatabases.HIKARI;
        config.setAddress("localhost");
        config.setPort(3306);
        config.setUser("root");
        config.setPassword("msLeague11@");
        config.setDatabase("dev");
        MySQLDatabase database = new MySQLDatabase(Executors.newSingleThreadExecutor());
        database.connect(config);
        database.execute(connection -> {
            connection.prepareStatement("SELECT 1").execute();
        });
    }

}

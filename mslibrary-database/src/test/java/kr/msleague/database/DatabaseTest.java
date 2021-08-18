package kr.msleague.database;


import java.sql.ResultSet;
import java.util.concurrent.Executors;

public class DatabaseTest {

    /*
    @Test
    public void mysqlTest(){
        DatabaseConfig config = MSLDatabases.HIKARI;
        config.setAddress("localhost");
        config.setPort(3306);
        config.setUser("root");
        config.setPassword("test");
        config.setDatabase("test");
        MySQLDatabase database = new MySQLDatabase(Executors.newSingleThreadExecutor());
        database.connect(config);
        int value = database.execute(connection -> {
            ResultSet rs = connection.prepareStatement("SELECT 1").executeQuery();
            rs.next();
            return rs.getInt(1);
        });
        Assert.assertEquals(1, value);
    }
     */
}

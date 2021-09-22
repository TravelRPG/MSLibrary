import kr.msleague.mslibrary.misc.PropertiesLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropTest {
    public static void main(String[] args) throws Exception{
        File global = new File("C:\\Java\\test.properties");
        if(!global.exists())
            global.createNewFile();
        Properties fp = PropertiesLoader.readProperties(global);
        System.out.println(fp.get("test"));
        System.out.println(fp.get("test1"));

        Properties fpp = new Properties();
        fpp.load(new FileReader("C:\\Java\\test.properties"));
        System.out.println(fpp.get("test"));
        System.out.println(fpp.get("test1"));
        fpp.setProperty("um", "준식");
        fpp.store(new FileWriter(global), "Initial comment");

        Properties fpp3 = new Properties();
        fpp3.load(new FileReader("C:\\Java\\test.properties"));
        System.out.println(fpp3.getProperty("um"));
    }
}

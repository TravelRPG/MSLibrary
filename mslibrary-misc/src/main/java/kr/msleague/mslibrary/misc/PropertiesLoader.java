package kr.msleague.mslibrary.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesLoader {
    public static Properties readProperties(File f){
        Properties prop = new Properties();
        try(InputStreamReader is = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8)){
            prop.load(is);
        }catch(IOException ex){
            throw new RuntimeException("Unexpected file error occured while reading properties");
        }
        return prop;
    }
}

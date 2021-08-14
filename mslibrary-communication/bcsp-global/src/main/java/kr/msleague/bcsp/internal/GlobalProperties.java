package kr.msleague.bcsp.internal;


import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class GlobalProperties {
    @Getter
    @Setter
    private static ServerType type;
    private static HashMap<String, String> propertiesMap = new HashMap<>();
    private static File file;
    public static void loadProperties(File f, Consumer<List<String>> defaultProp){
        file = new File(f, "settings.properties");
        try{
            if(!f.exists())
                f.mkdir();
            generateDefaultProperties(defaultProp);
            load();
        }catch(IOException ex){
            BCSPLogManager.getLogger().err("Failed to load properties file.");
            ex.printStackTrace();
        }
    }
    private static void generateDefaultProperties(Consumer<List<String>> defaultProp) throws IOException{
        if(!file.exists()){
            try{
                BCSPLogManager.getLogger().info("Creating new properties file..");
                file.createNewFile();
                BufferedWriter br = new BufferedWriter(new FileWriter(file));
                ArrayList<String> propList = new ArrayList<>();
                defaultProp.accept(propList);
                for(String elem : propList){
                    br.write(elem+"\n");
                }
                br.close();
            }catch(IOException ex){
                BCSPLogManager.getLogger().err("Failed to create properties file.");
                ex.printStackTrace();
            }
        }
    }
    public static String getProperties(String key){
        return propertiesMap.get(key);
    }
    private static void load() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while((line=br.readLine())!=null){
            if(line.contains("=")){
                String[] splitted = line.split("=");
                if(splitted.length == 2){
                    propertiesMap.put(splitted[0], splitted[1]);
                }
            }
        }
        br.close();
    }

}

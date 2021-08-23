package kr.msleague.mslibrary.customitem.impl.database;

import kr.msleague.mslibrary.customitem.api.MSItemData;
import kr.msleague.mslibrary.customitem.impl.serializers.JsonSerializer;
import kr.msleague.mslibrary.customitem.impl.serializers.YamlSerializer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

public class YamlFileItemDatabase extends AbstractFileItemDatabase<YamlConfiguration> {


    public YamlFileItemDatabase(ExecutorService service, File directory) {
        super(service, directory, ".yml", new YamlSerializer(), new YamlFileSerializer());
    }

    @Override
    MSItemData readFile(File file) throws IOException {
        return adapter.deserialize(serializer.read(file));
    }

    @Override
    void writeFile(File file, MSItemData data) throws IOException {
        serializer.write(file, adapter.serialize(data));
    }

    public static class YamlFileSerializer extends JsonSerializer implements FileSerializer<YamlConfiguration> {

        @Override
        public YamlConfiguration read(File file) throws IOException {
            YamlConfiguration config = new YamlConfiguration();
            FileInputStream fileinputstream;

            try {
                fileinputstream = new FileInputStream(file);
                config.load(new InputStreamReader(fileinputstream, StandardCharsets.UTF_8));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
            return config;
        }

        @Override
        public void write(File file, YamlConfiguration yaml) throws IOException {
            try {
                Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
                fileWriter.write(yaml.saveToString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

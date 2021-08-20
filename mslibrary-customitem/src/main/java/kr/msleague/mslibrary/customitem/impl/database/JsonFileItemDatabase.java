package kr.msleague.mslibrary.customitem.impl.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import kr.msleague.mslibrary.customitem.api.*;
import kr.msleague.mslibrary.customitem.impl.serializers.JsonSerializer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class JsonFileItemDatabase extends AbstractFileItemDatabase<JsonObject> {

    private static Gson gson = new Gson();

    public JsonFileItemDatabase(ExecutorService service, File directory) {
        super(service, directory, ".json", new JsonSerializer(), new JsonFileSerializer());
    }


    @Override
    MSItemData readFile(File file) throws IOException {
        return adapter.deserialize(serializer.read(file));
    }

    @Override
    void writeFile(File file, MSItemData data) throws IOException {
        serializer.write(file, adapter.serialize(data));
    }


    public static class JsonFileSerializer extends JsonSerializer implements FileSerializer<JsonObject>{

        @Override
        public JsonObject read(File file) throws IOException{
            FileReader reader = new FileReader(file);
            return gson.fromJson(new JsonReader(reader), JsonObject.class);
        }

        @Override
        public void write(File file, JsonObject jsonObject)  throws IOException{
            FileWriter writer = new FileWriter(file);
            gson.toJson(jsonObject, new JsonWriter(writer));
        }
    }
}

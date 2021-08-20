package kr.msleague.mslibrary.customitem.impl.database;

import kr.msleague.mslibrary.customitem.api.*;
import lombok.AccessLevel;
import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class AbstractFileItemDatabase<T> implements ItemDatabase {

    @Getter(AccessLevel.MODULE)
    final FileSerializer<T> serializer;
    @Getter(AccessLevel.MODULE)
    final File directory;
    @Getter(AccessLevel.MODULE)
    String fileFormat;
    ItemSerializer<T> adapter;
    ExecutorService service;
    AtomicInteger index;

    AbstractFileItemDatabase(ExecutorService service, File directory, String fileFormat, ItemSerializer<T> itemSerializer, FileSerializer<T> serializer){
        this.serializer = serializer;
        this.service = service;
        this.adapter = itemSerializer;
        this.fileFormat = fileFormat;
        if(directory.isDirectory()){
            this.directory = directory;
            this.index = new AtomicInteger(Objects.requireNonNull(directory.listFiles()).length);
        }else
            throw new IllegalArgumentException("the file should be directory");
    }

    @Override
    public Future<List<MSItemData>> loadAll() {
        return service.submit(this::loadAllInternally);
    }

    List<MSItemData> loadAllInternally(){
        List<MSItemData> datas = new ArrayList<>();
        List<File> files = Arrays.stream(Objects.requireNonNull(directory.listFiles())).sorted((f1, f2)->{
            char a = f1.getName().charAt(0);
            char b = f2.getName().charAt(0);
            if(a < b){
                return 1;
            }else if(a > b){
                return -1;
            }else{
                return 0;
            }
        }).collect(Collectors.toList());
        for(File file : files){
            try {
                datas.add(readFile(file));
            }catch (IOException | IllegalArgumentException ignored){

            }
        }
        return datas;
    }

    @Override
    public Future<Boolean> newItem(int id, @Nonnull MSItemData item) {
        return service.submit(()->{
            try {
                File file = new File(directory.getAbsolutePath() + "/" + id +fileFormat);
                if (file.exists()) {
                    throw new IllegalArgumentException("the file already exist");
                }else{
                    if(file.mkdir()) {
                        writeFile(file, item);
                        return true;
                    }
                }
            }catch (IOException ignored){

            }
            return false;
        });

    }

    @Override
    public Future<MSItemData> load(int itemID) throws IllegalStateException {
        return service.submit(()->{
            try {
                String fileName = itemID + fileFormat;
                File file = new File(directory.getAbsolutePath() + "/" + fileName);
                return readFile(file);
            }catch (IOException e){
                return null;
            }
        });
    }

    @Override
    public Future<Void> insertItem(int itemID, @Nonnull MSItemData item) throws IllegalArgumentException {
        return service.submit(()->{
            try {
                String fileName = itemID + fileFormat;
                File file = new File(directory.getAbsolutePath() + "/" + fileName);
                if(file.exists()){
                    return null;
                }else {
                    writeFile(file, item);
                }
            }catch (IOException e){

            }
            return null;
        });
    }

    @Override
    public Future<Void> deleteItem(int itemID) {
        return service.submit(()->{
            String fileName = itemID + fileFormat;
            File file = new File(directory.getAbsolutePath() + "/" + fileName);
            if(file.exists()){
                file.delete();
            }
            return null;
        });
    }

    @Override
    public Future<List<Integer>> search(String path, String value) {
        return service.submit(()->{
            List<MSItemData> list = loadAllInternally();
            List<Integer> result = new ArrayList<>();
            for(MSItemData data : list){
                if(data.getNodes().has(path)){
                    ItemElement element = data.getNodes().get(path);
                    if(element instanceof ItemNodeValue && element.asValue().getAsString().equals(value)){
                        result.add(data.getID());
                    }
                }
            }
            return result;
        });
    }

    @Override
    public Future<Void> modify(int itemID, @Nonnull String node, @Nullable String value) {
        return service.submit(()->{
            try {
                String fileName = itemID + ".json";
                File file = new File(directory.getAbsolutePath() + "/" + fileName);
                MSItemData data = readFile(file);
                if(value != null)
                    data.getNodes().setPrimitive(node, value);
                else
                    data.getNodes().set(node, null);
                writeFile(file, data);
            }catch (IOException e){

            }
            return null;
        });
    }

    @Override
    public Future<Boolean> has(int itemID) {
        return service.submit(()-> {
            String fileName = itemID + ".json";
            File file = new File(directory.getAbsolutePath() + "/" + fileName);
            return file.exists();
        });
    }

    abstract MSItemData readFile(File file) throws IOException;

    abstract void writeFile(File file, MSItemData t) throws IOException;

}

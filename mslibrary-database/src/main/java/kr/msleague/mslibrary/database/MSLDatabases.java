package kr.msleague.mslibrary.database;

import com.mongodb.lang.Nullable;
import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.api.MSDatabase;
import kr.msleague.mslibrary.database.impl.internal.DefaultHikariConfig;
import kr.msleague.mslibrary.database.impl.internal.MySQLDatabase;
import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 데이터베이스 모음
 */
public class MSLDatabases {

    public static final DatabaseConfig HIKARI = new DefaultHikariConfig();

    @Getter
    private static final MSLDatabases inst = new MSLDatabases();

    private final ConcurrentHashMap<String, MSDatabase<?>> databases = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, DatabaseConfig> defaultConfigs = new ConcurrentHashMap<>();


    private MSLDatabases(){
        addDefaultConfig("hikari-default", HIKARI);
    }

    public void add(@NonNull String name, @NonNull MSDatabase<?> database) throws IllegalArgumentException{
        if(databases.containsKey("name"))
            throw new IllegalArgumentException("the name "+name+" is already exist");
        else
            databases.put(name, database);
    }

    public MSDatabase<?> remove(@NonNull String name){
        if(databases.containsKey("name"))
            return databases.remove(name);
        else
            throw new IllegalArgumentException("the name "+name+" is not exist");
    }

    @Nullable
    public MSDatabase<?> get(String name){
        return databases.get(name);
    }

    @Nullable
    public <T> MSDatabase<T> get(Class<T> clazz, String name){
        return (MSDatabase<T>) databases.get(name);
    }

    @Nullable
    public DatabaseConfig getDefaultConfig(@NonNull String name){
        return defaultConfigs.get(name);
    }

    @Nullable
    public void addDefaultConfig(@NonNull String name, @NonNull DatabaseConfig defaultConfig){
        if(defaultConfigs.containsKey(name))
            throw new IllegalArgumentException("the name "+name+" is already exist");
        else
            defaultConfigs.put(name, defaultConfig);
    }

    public void removeDefaultConfig(@NonNull String name){
        defaultConfigs.remove(name);
    }

}

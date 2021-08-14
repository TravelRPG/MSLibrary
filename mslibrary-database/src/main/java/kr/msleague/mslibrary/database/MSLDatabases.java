package kr.msleague.mslibrary.database;

import kr.msleague.mslibrary.database.api.DatabaseConfig;
import kr.msleague.mslibrary.database.api.MSDatabase;

import java.util.concurrent.ConcurrentHashMap;

public class MSLDatabases {

    final ConcurrentHashMap<String, MSDatabase<?>> databases = new ConcurrentHashMap<>();
    final ConcurrentHashMap<String, DatabaseConfig> defaultConfigs = new ConcurrentHashMap<>();

}

package kr.msleague.mslibrary.database.impl;

import kr.msleague.mslibrary.database.api.DatabaseConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Properties;

@RequiredArgsConstructor
public class HashDatabaseConfig extends AbstractDatabaseConfig {

    private final Properties properties;

    public HashDatabaseConfig() {
        properties = new Properties();
    }

    @Override
    public DatabaseConfig setManually(@NonNull String key, String value) {
        properties.setProperty(key, value);
        return this;
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public DatabaseConfig copy() {
        return new HashDatabaseConfig((Properties) properties.clone());
    }
}

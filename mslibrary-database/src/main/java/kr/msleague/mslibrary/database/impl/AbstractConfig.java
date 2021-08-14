package kr.msleague.mslibrary.database.impl;

import kr.msleague.mslibrary.database.api.DatabaseConfig;

import java.util.Properties;

public class AbstractConfig implements DatabaseConfig {

    private final Properties properties;

    @Override
    public void setManually(String key, String value) {
        properties.setProperty(key, value);
    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }

    @Override
    public DatabaseConfig copy() {
        return properties.clone();
    }
}

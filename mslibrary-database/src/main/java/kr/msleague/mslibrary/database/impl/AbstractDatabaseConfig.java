package kr.msleague.mslibrary.database.impl;

import kr.msleague.mslibrary.database.api.DatabaseConfig;

import javax.annotation.Nullable;

public abstract class AbstractDatabaseConfig implements DatabaseConfig {

    @Override
    public String getAddress() {
        return get("address");
    }

    @Override
    public DatabaseConfig setAddress(String address) {
        setManually("address", address);
        return this;
    }

    @Nullable
    @Override
    public String getUser() {
        return get("user");
    }

    @Override
    public DatabaseConfig setUser(String user) {
        setManually("user", user);
        return this;
    }

    @Nullable
    @Override
    public String getPort() {
        return get("port");
    }

    @Override
    public DatabaseConfig setPort(int port) {
        setManually("port", port + "");
        return this;
    }

    @Nullable
    @Override
    public String getPassword() {
        return get("password");
    }

    @Override
    public DatabaseConfig setPassword(String password) {
        setManually("password", password);
        return this;
    }


    @Nullable
    @Override
    public String getDatabase() {
        return get("database");
    }

    @Override
    public DatabaseConfig setDatabase(String database) {
        setManually("database", database);
        return this;
    }
}

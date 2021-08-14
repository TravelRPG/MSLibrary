package kr.msleague.mslibrary.database.impl;

import com.sun.istack.internal.Nullable;
import kr.msleague.mslibrary.database.api.DatabaseConfig;

public abstract class AbstractDatabaseConfig implements DatabaseConfig {

    @Override
    public String getAddress(){
        return get("address");
    }

    @Override
    public void setAddress(String address){
        setManually("address", address);
    }

    @Nullable
    @Override
    public String getUser(){
        return get("user");
    }

    @Override
    public void setUser(String user){
        setManually("user", user);
    }

    @Nullable
    @Override
    public String getPort(){
        return get("port");
    }

    @Override
    public void setPort(int port){
        setManually("port", port+"");
    }

    @Nullable
    @Override
    public String getPassword(){
        return get("password");
    }

    @Override
    public void setPassword(String password){
        setManually("password", password);
    }


    @Nullable
    @Override
    public String getDatabase(){
        return get("database");
    }

    @Override
    public void setDatabase(String database){
        setManually("database", database);
    }
}

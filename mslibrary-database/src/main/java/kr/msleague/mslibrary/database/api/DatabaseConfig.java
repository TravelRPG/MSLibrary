package kr.msleague.mslibrary.database.api;

import com.sun.istack.internal.Nullable;

/**
 * MS 데이터베이스 설정
 */
public interface DatabaseConfig {

    /**
     * 데이터베이스 주소를 반환합니다.
     * @return 데이터베이스 주소
     */
    @Nullable
    default String getAddress(){
        return get("address");
    }

    /**
     * 데이터베이스 주소를 설정합니다.
     * @param address 데이터베이스 주소
     */
    default void setAddress(String address){
        setManually("address", address);
    }

    /**
     * 데이터베이스 유저를 받아옵니다.
     * @return 데이터베이스 유저 이름
     */
    @Nullable
    default String getUser(){
        return get("user");
    }

    /**
     *
     * @param user
     */
    default void setUser(String user){
        setManually("user", user);
    }

    default int getPort(){
        return Integer.parseInt(get("port"));
    }

    default void setPort(int port){
        setManually("port", port+"");
    }

    default String getPassword(){
        return get("password");
    }

    default void setPassword(String password){
        setManually("password", password);
    }

    default String getDatabase(){
        return get("database");
    }

    default void setDatabase(String database){
        setManually("database", database);
    }

    void setManually(String key, String value);

    String get(String key);

    DatabaseConfig copy();
}

package kr.msleague.mslibrary.database.api;

import com.sun.istack.internal.Nullable;
import lombok.NonNull;

import java.util.Optional;

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
     * 데이터베이스 유저를 반환합니다.
     * @return 데이터베이스 유저 이름
     */
    @Nullable
    default String getUser(){
        return get("user");
    }

    /**
     * 데이터베이스 유저를 설정합니다.
     * @param user 데이터베이스 유저 이름
     */
    default void setUser(String user){
        setManually("user", user);
    }

    /**
     * 데이터베이스 포트를 반환합니다.
     * @return 데이터베이스 포트
     */
    @Nullable
    default String getPort(){
        return get("port");
    }

    /**
     * 데이터베이스 포트를 설정합니다.
     * @param port 데이터베이스 포트
     */
    default void setPort(int port){
        setManually("port", port+"");
    }

    /**
     * 데이터베이스 비밀번호를 반환합니다.
     * @return 데이터베이스 비밀번호
     */
    @Nullable
    default String getPassword(){
        return get("password");
    }

    /**
     * 데이터베이스 비밀번호를 설정합니다.
     * @param password 데이터베이스 비밀번호
     */
    default void setPassword(String password){
        setManually("password", password);
    }

    /**
     * 데이터베이스 이름을 반환합니다.
     * @return 데이터베이스 이름
     */
    @Nullable
    default String getDatabase(){
        return get("database");
    }

    /**
     * 데이터베이스 이름을 설정합니다.
     * @param database 데이터베이스 이름
     */
    default void setDatabase(String database){
        setManually("database", database);
    }

    /**
     * 데이터베이스 설정 값을 직접 수정합니다.
     * @param key 설정 이름
     * @param value 설정 값
     */
    void setManually(@NonNull String key, @Nullable String value);

    /**
     * 데이터베이스 설정 값을 받아옵니다.
     * @param key 설정 이름
     * @return 설정 값
     */
    @Nullable
    String get(String key);

    /**
     * 데이터베이스 설정 값을 받아옵니다.
     * @param key 설정 이름
     * @param defaultValue 기본 값
     * @return 값이 있을 경우 설정 값, 없을 경우 입력받은 기본 값
     */
    default String get(@NonNull String key, @NonNull String defaultValue){
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

    /**
     * 설정을 복사합니다.
     * @return 복사 된 데이터베이스 설정
     */
    DatabaseConfig copy();
}

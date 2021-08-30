package kr.msleague.mslibrary.database.api;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * MS 데이터베이스 설정
 */
public interface DatabaseConfig {

    /**
     * 데이터베이스 주소를 반환합니다.
     *
     * @return 데이터베이스 주소
     */
    @Nullable
    String getAddress();

    /**
     * 데이터베이스 주소를 설정합니다.
     *
     * @param address 데이터베이스 주소
     * @return 설정 인스턴스
     */
    DatabaseConfig setAddress(String address);

    /**
     * 데이터베이스 유저를 반환합니다.
     *
     * @return 데이터베이스 유저 이름
     */
    @Nullable
    String getUser();

    /**
     * 데이터베이스 유저를 설정합니다.
     *
     * @param user 데이터베이스 유저 이름
     * @return 설정 인스턴스
     */
    DatabaseConfig setUser(String user);

    /**
     * 데이터베이스 포트를 반환합니다.
     *
     * @return 데이터베이스 포트
     */
    @Nullable
    String getPort();

    /**
     * 데이터베이스 포트를 설정합니다.
     *
     * @param port 데이터베이스 포트
     * @return 설정 인스턴스
     */
    DatabaseConfig setPort(int port);

    /**
     * 데이터베이스 비밀번호를 반환합니다.
     *
     * @return 데이터베이스 비밀번호
     */
    @Nullable
    String getPassword();

    /**
     * 데이터베이스 비밀번호를 설정합니다.
     *
     * @param password 데이터베이스 비밀번호
     * @return 설정 인스턴스
     */
    DatabaseConfig setPassword(String password);

    /**
     * 데이터베이스 이름을 반환합니다.
     *
     * @return 데이터베이스 이름
     */
    @Nullable
    String getDatabase();

    /**
     * 데이터베이스 이름을 설정합니다.
     *
     * @param database 데이터베이스 이름
     */
    DatabaseConfig setDatabase(String database);

    /**
     * 데이터베이스 설정 값을 직접 수정합니다.
     *
     * @param key   설정 이름
     * @param value 설정 값
     * @return 설정 인스턴스
     */
    DatabaseConfig setManually(@NonNull String key, @Nullable String value);

    /**
     * 데이터베이스 설정 값을 받아옵니다.
     *
     * @param key 설정 이름
     * @return 설정 값
     */
    @Nullable
    String get(String key);

    /**
     * 데이터베이스 설정 값을 받아옵니다.
     *
     * @param key          설정 이름
     * @param defaultValue 기본 값
     * @return 값이 있을 경우 설정 값, 없을 경우 입력받은 기본 값
     */
    default String get(@NonNull String key, @NonNull String defaultValue) {
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

    /**
     * 설정을 복사합니다.
     *
     * @return 복사 된 데이터베이스 설정
     */
    DatabaseConfig copy();
}

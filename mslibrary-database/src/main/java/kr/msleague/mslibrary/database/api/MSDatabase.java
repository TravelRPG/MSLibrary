package kr.msleague.mslibrary.database.api;

import kr.msleague.mslibrary.misc.ThrowingFunction;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.util.concurrent.Future;

/**
 * MS 데이터베이스 인터페이스
 * @param <T> 데이터베이스 커넥션 타입 ( Connection , MongoCollection, RedisCommands 같은거 ㅇㅇ )
 */
public interface MSDatabase<T> {

    /**
     * 데이터베이스를 연결합니다.
     * @param config 주어진 데이터베이스 설정
     * @return 연결 성공 여부
     */
    boolean connect(DatabaseConfig config);

    /**
     * 로직을 비동기로 실행합니다.
     * @param function 실행할 로직
     * @param <R> 반환 값의 타입, 실행 중 문제가 발생할 경우 null
     * @return 실행 완료 시 반환 값
     */
    @Nullable
    <R> Future<R> executeAsync(ThrowingFunction<T, R> function);

    /**
     * 로직을 동기로 실행합니다.
     * @param function 실행할 로직
     * @param <R> 반환 값의 타입, 실행 중 문제가 발생할 경우 null
     * @return 실행 완료 시 반환 값
     */
    @Nullable
    <R> R execute(ThrowingFunction<T, R> function);
}

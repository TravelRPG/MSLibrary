package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * 아이템 허브 코어 인터페이스.
 * - 아이템을 통합적으로 관리하는 통합 API 제공.
 * - Bukkit API와 분리된 아이템 생성, 조회, 수정, 삭제 기능 제공.
 * @param <T> 얻고자 하는 아이템 타입
 * @since 1.0
 * @author Arkarnag
 */
public interface ItemCenter<T> {

    /**
     * 아이템 데이터베이스 인스턴스를 가져옵니다.
     * @return 아이템 데이터베이스
     */
    ItemDatabase getDatabase();

    /**
     * 아이템 허브에서 사용하는 아이템 팩토리 객체를 가져옵니다.
     * @return 아이템 팩토리 인스턴스. 존재하지 않을 경우 null
     */
    @Nullable
    ItemFactory<T> getFactory();

    /**
     * 아이템을 해당 아이템 인스턴스로 찾습니다.
     * 비동기 처리 중, 직렬화에 문제가 생겼을 경우, ExecutionException을 호출합니다.
     * @param id 아이템 고유 번호
     * @return 비동기 처리 값
     */
    Optional<T> getItem(int id);

    /**
     * 아이템을 주어진 경로와 피라미터를 통해 찾습니다.
     * @param clazz 얻고자 하는 인스턴스 타입 클래스
     * @param path 찾고자 하는 아이템 노드 경로
     * @param value 찾고자 하는 아이템 노드 값
     * @return 찾은 결과 값 리스트의 비동기 값
     */
    @Nonnull
    List<T> getItems(@Nonnull Class<T> clazz, @Nonnull String path, @Nonnull String value);

    /**
     * 데이터베이스의 모든 아이템을 로드합니다.
     * @param async 비동기 실행 여부
     */
    void load(boolean async);

    /**
     * 헤당 아이템의 정보를 새로고침합니다.
     * @param itemID 아이템 고유 아이디
     * @param async 비동기 실행 여부
     */
    void refresh(int itemID, boolean async);

}

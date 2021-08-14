package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;

/**
 * 아이템 직렬화 역직렬화 스트레티지 클래스.
 * 아이템의 직렬화와 역직렬화 로직을 제공합니다.
 * @param <T> 역직렬화 목표 타입.
 * @since 1.0
 * @author arkarang
 */
public interface ItemAdapter<T extends HubItem> {

    /**
     * 아이템을 역직렬화합니다.
     * @param item 직렬화된 아이템
     * @return 역직렬화 된 아이템 인스턴스
     * @throws IllegalArgumentException 아이템 역직렬화 시 문제가 발생할 경우
     */
    @Nonnull
    T deserialize(@Nonnull SerializedItem item) throws IllegalArgumentException;

    /**
     * 아이템을 직렬화합니다.
     * @param t 아이템 인스턴스
     * @return 직렬화 된 아이템
     * @throws IllegalArgumentException 아이템 직렬화 중 문제가 발생할 경우
     */
    @Nonnull
    SerializedItem serialize(@Nonnull T t) throws IllegalArgumentException;
}

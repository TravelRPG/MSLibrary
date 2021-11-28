package kr.msleague.mslibrary.node.api;

import javax.annotation.Nonnull;

/**
 * 아이템 직렬화 역직렬화 스트레티지 클래스.
 * 아이템의 직렬화와 역직렬화 로직을 제공합니다.
 *
 * @param <T> 역직렬화 대상 타입.
 * @author arkarang
 * @since 1.0
 */
public interface MSNodeSerializer<T> {
    /**
     * 아이템을 역직렬화합니다.
     *
     * @param serialized 직렬화된 아이템 소스
     * @return 역직렬화 된 아이템 인스턴스
     * @throws IllegalArgumentException 아이템 역직렬화 시 문제가 발생할 경우
     */
    @Nonnull
    MSNode deserialize(@Nonnull T serialized) throws IllegalArgumentException;

    /**
     * 아이템을 직렬화합니다.
     *
     * @param item 아이템 인스턴스
     * @return 직렬화 된 아이템 소스
     * @throws IllegalArgumentException 아이템 직렬화 중 문제가 발생할 경우
     */
    @Nonnull
    T serialize(@Nonnull MSNode item) throws IllegalArgumentException;
}

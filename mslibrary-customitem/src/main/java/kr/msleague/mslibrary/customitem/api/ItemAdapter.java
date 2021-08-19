package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;

/**
 *
 * @param <T> 역직렬화 대상 타입.
 * @since 1.0
 * @author arkarang
 */
public interface ItemAdapter<T> {

    /**
     * 아이템 어뎁터를 적용합니다.
     * @param target 어뎁터를 적용할 대상
     * @param data 직렬화된 아이템 소스
     * @return 적용된 아이템 인스턴스
     * @throws IllegalArgumentException 아이템 역직렬화 시 문제가 발생할 경우
     */
    @Nonnull
    T read(@Nonnull T target, @Nonnull MSItemData data) throws IllegalArgumentException;

    @Nonnull
    MSItemData write(@Nonnull MSItemData data, @Nonnull T target);
}

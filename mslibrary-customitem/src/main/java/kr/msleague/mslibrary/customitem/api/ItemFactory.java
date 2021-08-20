package kr.msleague.mslibrary.customitem.api;

import kr.msleague.mslibrary.customitem.impl.MSItemStack;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * 아이템 팩토리
 * 아이템을 직접적으로 직렬화 역직렬화를 수행합니다.
 * 등록된 adapter를 통해 직렬화 역직렬화 로직을 수행합니다.
 * @param <T> 직렬화 목표 타입
 * @since 1.0
 * @author Arkarang
 */
public interface ItemFactory<T> {

    /**
     * 현재 등록되어 있는 Adapter 목록을 확인합니다.
     * @return adapter 목록
     */
    @Nonnull
    Map<String, ItemAdapter<T>> getAdapters();

    /**
     * adapter를 등록합니다.
     * @param tag 별명
     * @param adapter 등록할 adapter 객체
     */
    void add(@Nonnull String tag, @Nonnull ItemAdapter<T> adapter);

    /**
     * 아이템을 생성합니다.
     * @param data 생성할 아이템 데이터
     * @return 커스텀 아이템 객체
     * @throws IllegalArgumentException 아이템 역직렬화 중 오류가 발생할 경우
     */
    @Nonnull
    T build(@Nonnull MSItemData data) throws IllegalArgumentException;

    /**
     * 아이템을 생성합니다.
     * @param data 생성할 아이템 데이터
     * @param item 전달 받은 아이템 오브젝트
     * @return 커스텀 아이템 객체
     * @throws IllegalArgumentException 아이템 역직렬화 중 오류가 발생할 경우
     */
    @Nonnull
    T build(@Nonnull MSItemData data, T item) throws IllegalArgumentException;

    /**
     * 아이템을 데이터로 파싱합니다.
     * @param item 파싱할 아이템
     * @return 아이템 데이터
     * @throws IllegalArgumentException 아이템 직렬화 중 오류가 발생할 경우
     */
    MSItemData parse(T item) throws IllegalArgumentException;

    /**
     * 아이템을 데이터로 파싱합니다.
     * @param item 파싱할 아이템
     * @param data 전달 받은 아이템 데이터
     * @return 아이템 데이터
     * @throws IllegalArgumentException 아이템 직렬화 중 오류가 발생할 경우
     */
    MSItemData parse(T item, MSItemData data) throws IllegalArgumentException;
}

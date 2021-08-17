package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * ItemNode
 * 아이템의 설정값을 구성하는 단위입니다.
 */
public interface ItemNode extends ItemElement {

    /**
     * 아이템의 하위 엘리먼트를 리턴합니다.
     * @param path 아이템의 엘리먼트 경로
     * @return 아이템 엘리먼트. 존재하지 않을 경우 null.
     */
    @Nullable
    ItemElement get(@Nonnull String path);

    @Nullable
    ItemElement get(@Nonnull String path, boolean deep);

    /**
     * 아이템의 하위 노드를 설정합니다.
     * @param path 아이템의 노드 경로
     * @param node 아이템의 설정 노드 값 null 일 경우 해당 노드 삭제.

     */
    void set(@Nonnull String path, @Nullable ItemElement node);

    /**
     * 해당 키를 직접적으로 가지고 있는지 여부를 확인합니다.
     * @param key 찾고자 하는 키
     * @return 포함 여부
     */
    boolean has(@Nonnull String key);

    /**
     * 아이템이 가지고 있는 키를 반환합니다.
     * 하위 노드까지 체크하지 않습니다.
     * @return 직접적으로 가지고 있는 키 목록
     */
    @Nonnull
    List<String> getKeys();

}

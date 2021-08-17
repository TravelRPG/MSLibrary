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
     * @return 실행 성공 여부
     */
    boolean set(@Nonnull String path, @Nullable ItemElement node);

    //todo: documents this
    List<String> getKeys();

}

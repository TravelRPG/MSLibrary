package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    ItemNodeValue getElement(@Nonnull String path);

    /**
     * 아이템의 하위 노드를 리턴합니다.
     * @param path 아이템의 노드 경로
     * @return 아이템 노드. 존재하지 않을 경우 null.
     */
    @Nullable
    ItemNode getNode(@Nonnull String path);

    /**
     * 아이템의 하위 배열을 리턴합니다.
     * @param path 아이템의 배열 경로
     * @return 아이템 배열. 존재하지 않을 경우 빈 배열.
     */
    @Nonnull
    ItemNodeArray getArray(@Nonnull String path);

    /**
     * 아이템의 하위 노드를 설정합니다.
     * @param path 아이템의 노드 경로
     * @param node 아이템의 설정 노드 값 null 일 경우 해당 노드 삭제.
     * @return 실행 성공 여부
     */
    boolean set(@Nonnull String path, @Nullable ItemNode node);

    /**
     * 아이템의 하위 노드를 설정합니다.
     * @param path 아이템의 엘리먼트 경로
     * @param element 아이템의 설정 엘리먼트. 만일 값이 null 일 경우 해당 노드 삭제.
     * @return 실행 성공 여부
     */
    boolean set(@Nonnull String path, @Nullable ItemNodeValue element);
}

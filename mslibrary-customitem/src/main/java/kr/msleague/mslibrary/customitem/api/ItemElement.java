package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 아이템 직렬화 데이터 구송요소입니다.
 *
 * @author Arkarang
 * @since 1.0
 */
public interface ItemElement {

    /**
     * 조상 엘리먼트를 가져옵니다.
     *
     * @return 조상 엘리먼트, 없을 경우 null
     */
    @Nullable
    ItemElement getParents();

    /**
     * 현재 엘리먼트의 경로를 가져옵니다.
     *
     * @return 엘리먼트 경로
     */
    @Nonnull
    String getPath();

    String getName();

    //todo: documents this
    @Nonnull
    ItemNode asNode() throws UnsupportedOperationException;

    //todo: documents this
    @Nonnull
    ItemNodeArray asArray() throws UnsupportedOperationException;

    //todo: documents this
    @Nonnull
    ItemNodeValue asValue() throws UnsupportedOperationException;

}

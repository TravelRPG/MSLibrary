package kr.msleague.mslibrary.node.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 아이템 직렬화 데이터 구송요소입니다.
 *
 * @author Arkarang
 * @since 1.0
 */
public interface MSNodeElement {

    /**
     * 조상 엘리먼트를 가져옵니다.
     *
     * @return 조상 엘리먼트, 없을 경우 null
     */
    @Nullable
    MSNodeElement getParents();

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
    MSNode asNode() throws UnsupportedOperationException;

    //todo: documents this
    @Nonnull
    MSNodeArray asArray() throws UnsupportedOperationException;

    //todo: documents this
    @Nonnull
    MSNodeValue asValue() throws UnsupportedOperationException;

}

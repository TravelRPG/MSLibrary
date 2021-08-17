package kr.msleague.mslibrary.customitem.api;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 아이템 직렬화 데이터 구송요소입니다.
 * @since 1.0
 * @author Arkarang
 */
public interface ItemElement {

    /**
     * 조상 엘리먼트를 가져옵니다.
     * @return 조상 엘리먼트, 없을 경우 null
     */
    @Nullable
    ItemElement getParents();

    /**
     * 현재 엘리먼트의 경로를 가져옵니다.
     * @return 엘리먼트 경로
     */
    @Nonnull
    String getPath();

    String getName();

    //todo: documents this
    ItemNode asNode();

    //todo: documents this
    ItemNodeArray asArray();

    //todo: documents this
    ItemNodeValue asValue();

}

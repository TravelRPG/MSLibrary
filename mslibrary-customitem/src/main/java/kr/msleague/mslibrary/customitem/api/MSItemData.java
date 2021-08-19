package kr.msleague.mslibrary.customitem.api;

/**
 * 아이템 데이터 인터페이스입니다.
 * @since 1.0
 * @author Arkarang
 */
public interface MSItemData extends MSItem {

    //todo: documents this
    ItemNode getNodes();
}

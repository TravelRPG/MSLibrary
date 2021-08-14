package kr.msleague.mslibrary.customitem.api;

/**
 * 아이템 최상위 인터페이스입니다.
 * 아이템은 두가지 정보를 가집니다.
 * 아이템 고유 아이디 : 아이템의 고유한 아이디입니다. 유일합니다.
 * 아이템 버전 : 아이템의 직렬화 버전을 의미합니다. 아이템이 같더라도, 아이템의 버전은 다를 수 있습니다.
 * @since 1.0
 * @author Arkarang
 */
public interface HubItem {

    /**
     * 아이템 아이디를 리턴합니다.
     * @return 아이템 아이디
     */
    int getID();

    /**
     * 아이템의 버전을 리턴합니다.
     * @return 아이템 버전
     */
    long getVersion();
}

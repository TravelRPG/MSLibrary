package kr.msleague.message.api;

/**
 * 직접 플레이스 홀더를 만들 수 있는 인터페이스
 * 상속 받아 MSMessageLib#registerAdapters 에 전달하여 등록합니다.
 * @param <T> 전달 받을 객체 타입
 */
public interface MSMessageAdapter<T> {
    /**
     * 플레이스 홀더를 직접 구성할 수 있습니다.
     * @param origin 원본 메세지
     * @param obj 받을 객체
     * @return String
     */
    public String reformat(String origin, T obj);
}

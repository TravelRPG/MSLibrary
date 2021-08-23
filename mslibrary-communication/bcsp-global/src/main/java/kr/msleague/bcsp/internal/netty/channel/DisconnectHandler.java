package kr.msleague.bcsp.internal.netty.channel;

public interface DisconnectHandler {
    /**
     * 채널 연결이 끊어졌을때 실행할 구문을 람다/인터페이스 형식으로 작성할 수 있습니다
     *
     * @param wrapper 연결이 끊어진 채널 래퍼입니다.
     */
    void onDisconnect(ChannelWrapper wrapper);
}

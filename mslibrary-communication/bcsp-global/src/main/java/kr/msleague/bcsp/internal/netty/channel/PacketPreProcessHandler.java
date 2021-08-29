package kr.msleague.bcsp.internal.netty.channel;

import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

public interface PacketPreProcessHandler {
    /**
     * 패킷이 들어왔을때 선처리 프로세서를 설정할 수 있습니다
     *
     * @param packet  들어온 패킷입니다
     * @param wrapper 들어온 채널입니다
     */
    void preprocess(AbstractPacket packet, ChannelWrapper wrapper);
}

package kr.msleague.bcsp.internal.netty.channel;

import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

public interface PacketPreProcessHandler {
    void preprocess(AbstractPacket packet, ChannelWrapper wrapper);
}

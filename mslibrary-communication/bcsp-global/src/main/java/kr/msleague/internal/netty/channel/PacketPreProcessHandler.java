package kr.msleague.internal.netty.channel;

import kr.msleague.internal.netty.packet.AbstractPacket;

public interface PacketPreProcessHandler {
    void preprocess(AbstractPacket packet, ChannelWrapper wrapper);
}

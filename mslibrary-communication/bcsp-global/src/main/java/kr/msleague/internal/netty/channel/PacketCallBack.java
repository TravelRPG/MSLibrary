package kr.msleague.internal.netty.channel;

import kr.msleague.internal.netty.packet.AbstractPacket;

public interface PacketCallBack<T extends AbstractPacket> {
    void onCallBackRecieved(T t);
}

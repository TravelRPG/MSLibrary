package kr.msleague.bcsp.internal.netty.channel;

import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

public interface PacketCallBack<T extends AbstractPacket> {
    void onCallBackRecieved(T t);
}

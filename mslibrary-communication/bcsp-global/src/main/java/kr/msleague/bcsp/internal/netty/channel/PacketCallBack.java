package kr.msleague.bcsp.internal.netty.channel;

import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

public interface PacketCallBack<T extends AbstractPacket> {
    /**
     * The interface used to packet callback
     * @param t Target packet
     */
    void onCallBackRecieved(T t);
}

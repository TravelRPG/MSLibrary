package kr.msleague.bcsp.internal.netty.exception;

import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

public class PacketEncodeException extends PacketException {
    public PacketEncodeException(AbstractPacket packet, String message) {
        super(packet, message);
    }
}

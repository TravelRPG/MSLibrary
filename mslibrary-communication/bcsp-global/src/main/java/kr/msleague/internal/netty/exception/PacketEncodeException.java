package kr.msleague.internal.netty.exception;

import kr.msleague.internal.netty.packet.AbstractPacket;

public class PacketEncodeException extends PacketException{
    public PacketEncodeException(AbstractPacket packet, String message) {
        super(packet, message);
    }
}

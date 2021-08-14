package kr.msleague.internal.netty.exception;

import kr.msleague.internal.netty.packet.AbstractPacket;

public class PacketException extends RuntimeException{
    private AbstractPacket packet;
    public PacketException(AbstractPacket packet, String message){
        super(message);
        this.packet = packet;
    }
    public PacketException(AbstractPacket packet){
        super();
        this.packet = packet;
    }
}

package kr.msleague.bukkit.event.packet;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.packet.AbstractPacket;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketReceivedEvent extends Event {
    private static final HandlerList list = new HandlerList();
    @Getter
    private AbstractPacket packet;
    @Getter
    private ChannelWrapper wrapper;
    public PacketReceivedEvent(AbstractPacket packet, ChannelWrapper wrapper) {
        this.packet = packet;
        this.wrapper = wrapper;
    }
    private boolean isPacketOf(Class<? extends AbstractPacket> clazz){
        return clazz.isInstance(packet);
    }
    @Override
    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList getHandlerList(){
        return list;
    }
}

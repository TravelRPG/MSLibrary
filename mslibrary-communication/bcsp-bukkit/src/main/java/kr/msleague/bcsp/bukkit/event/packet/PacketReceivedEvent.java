package kr.msleague.bcsp.bukkit.event.packet;

import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
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

    public static HandlerList getHandlerList() {
        return list;
    }

    private boolean isPacketOf(Class<? extends AbstractPacket> clazz) {
        return clazz.isInstance(packet);
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}

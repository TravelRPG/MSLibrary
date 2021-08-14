package kr.msleague.proxy.event.packet;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.packet.AbstractPacket;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

public class ProxyPacketReceivedEvent extends Event {
    @Getter
    private AbstractPacket packet;
    @Getter
    private ChannelWrapper wrapper;
    public ProxyPacketReceivedEvent(AbstractPacket packet, ChannelWrapper wrapper) {
        this.packet = packet;
        this.wrapper = wrapper;
    }
    private boolean isPacketOf(Class<? extends AbstractPacket> clazz){
        return clazz.isInstance(packet);
    }
}

package kr.msleague.bukkit.event.connection;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.pipeline.ConnectionState;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BCSPConnectionEvent extends Event {
    private static final HandlerList list = new HandlerList();
    @Getter
    private ChannelWrapper wrapper;
    @Getter
    private ConnectionState state;
    public BCSPConnectionEvent(ChannelWrapper wrapper, ConnectionState state){
        this.wrapper = wrapper;
        this.state = state;
    }
    @Override
    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList getHandlerList(){
        return list;
    }
}

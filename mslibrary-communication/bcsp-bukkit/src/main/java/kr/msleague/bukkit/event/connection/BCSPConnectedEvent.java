package kr.msleague.bukkit.event.connection;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.pipeline.ConnectionState;
import org.bukkit.event.HandlerList;

public class BCSPConnectedEvent extends BCSPConnectionEvent{
    private static final HandlerList list = new HandlerList();
    public BCSPConnectedEvent(ChannelWrapper wrapper, ConnectionState state) {
        super(wrapper, state);
    }
    @Override
    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList getHandlerList(){
        return list;
    }
}

package kr.msleague.bcsp.bukkit.event.connection;

import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import org.bukkit.event.HandlerList;

public class BCSPDisconnectedEvent extends BCSPConnectionEvent{
    private static final HandlerList list = new HandlerList();
    public BCSPDisconnectedEvent(ChannelWrapper wrapper, ConnectionState state) {
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

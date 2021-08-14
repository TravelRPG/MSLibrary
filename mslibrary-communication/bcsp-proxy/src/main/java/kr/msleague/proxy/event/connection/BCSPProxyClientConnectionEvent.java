package kr.msleague.proxy.event.connection;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.pipeline.ConnectionState;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

@Getter
public class BCSPProxyClientConnectionEvent extends Event {
    private ChannelWrapper wrapper;
    private ConnectionState state;
    public BCSPProxyClientConnectionEvent(ChannelWrapper wrapper, ConnectionState state){
        this.wrapper = wrapper;
        this.state = state;
    }
}

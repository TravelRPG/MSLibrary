package kr.msleague.proxy.event.connection;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.pipeline.ConnectionState;
import lombok.Getter;

@Getter
public class BCSPProxyClientConnectedEvent extends BCSPProxyClientConnectionEvent{
    private String serverName;
    public BCSPProxyClientConnectedEvent(ChannelWrapper wrapper, ConnectionState state, String serverName) {
        super(wrapper, state);
        this.serverName = serverName;
    }
}

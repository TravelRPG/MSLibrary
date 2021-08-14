package kr.msleague.bcsp.proxy.event.connection;

import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import lombok.Getter;

@Getter
public class BCSPProxyClientConnectedEvent extends BCSPProxyClientConnectionEvent{
    private String serverName;
    public BCSPProxyClientConnectedEvent(ChannelWrapper wrapper, ConnectionState state, String serverName) {
        super(wrapper, state);
        this.serverName = serverName;
    }
}

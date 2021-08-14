package kr.msleague.proxy.event.connection;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.pipeline.ConnectionState;

public class BCSPProxyClientDisconnectedEvent extends BCSPProxyClientConnectionEvent{
    public BCSPProxyClientDisconnectedEvent(ChannelWrapper wrapper, ConnectionState state) {
        super(wrapper, state);
    }
}

package kr.msleague.bcsp.proxy.event.connection;

import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;

public class BCSPProxyClientDisconnectedEvent extends BCSPProxyClientConnectionEvent {
    public BCSPProxyClientDisconnectedEvent(ChannelWrapper wrapper, ConnectionState state) {
        super(wrapper, state);
    }
}

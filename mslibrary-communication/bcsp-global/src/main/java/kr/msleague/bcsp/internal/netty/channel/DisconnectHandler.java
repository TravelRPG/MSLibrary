package kr.msleague.bcsp.internal.netty.channel;

public interface DisconnectHandler {
    void onDisconnect(ChannelWrapper wrapper);
}

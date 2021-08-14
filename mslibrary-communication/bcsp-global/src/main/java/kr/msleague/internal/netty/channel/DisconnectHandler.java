package kr.msleague.internal.netty.channel;

public interface DisconnectHandler {
    void onDisconnect(ChannelWrapper wrapper);
}

package kr.msleague.internal.netty.pipeline;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import kr.msleague.internal.logger.BCSPLogManager;
import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.channel.DisconnectHandler;
import kr.msleague.internal.netty.channel.PacketPreProcessHandler;
import kr.msleague.internal.netty.exception.ByteBufException;
import kr.msleague.internal.netty.exception.PacketException;
import kr.msleague.internal.netty.packet.AbstractPacket;
import kr.msleague.internal.netty.packet.Direction;
import kr.msleague.internal.netty.packet.PacketMapper;
import kr.msleague.internal.netty.packet.sys.HandShakePacket;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class BossHandler extends ChannelInboundHandlerAdapter {
    @Setter
    private PacketPreProcessHandler packetPreProcessHandler;
    @Setter
    private DisconnectHandler disconnectHandler;
    @Getter
    private ConnectionState connectionState;
    @Getter
    private ChannelWrapper wrapper;
    public BossHandler(Channel channel){
        this.wrapper = new ChannelWrapper(this, channel);
        this.connectionState = ConnectionState.IDLE;
    }
    public void setConnectionState(ConnectionState state){
        this.connectionState = state;
        BCSPLogManager.getLogger().info("Internal netty connection state is now : " +state.toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(this.connectionState != ConnectionState.CONNECTED && !(msg instanceof HandShakePacket)){
            BCSPLogManager.getLogger().err("BCSP recieved packet before handshake. Recieved packet dropped.");
            return;
        }
        AbstractPacket packet = (AbstractPacket) msg;
        if(packetPreProcessHandler != null)
            packetPreProcessHandler.preprocess(packet, this.getWrapper());
        if(packet.isCallBackResult() && getWrapper().getCallBackContainer().complete(packet)){
            return;
        }
        Direction.INBOUND.onPacketRecieved(packet, wrapper);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof IOException){
            BCSPLogManager.getLogger().err("BCSP IOException");
        }else if(cause instanceof ByteBufException){
            BCSPLogManager.getLogger().err("BCSP Encountered exception while ByteBuf R/W: " + cause.getMessage());
        }else if(cause instanceof PacketException){
            BCSPLogManager.getLogger().err("BCSP Encountered exception while Packet R/W: " + cause.getMessage());
        }else if(cause instanceof ReadTimeoutException) {
            BCSPLogManager.getLogger().err("BCSP Encountered read timeout exception. Maybe connection broke?");
        }else {
            BCSPLogManager.getLogger().err("BCSP Encountered Exception");
        }
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        setConnectionState(ConnectionState.IDLE);
        if(disconnectHandler != null){
            disconnectHandler.onDisconnect(this.getWrapper());
        }
    }
}

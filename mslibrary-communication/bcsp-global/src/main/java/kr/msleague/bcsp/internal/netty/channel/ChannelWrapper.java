package kr.msleague.bcsp.internal.netty.channel;


import io.netty.channel.Channel;
import kr.msleague.bcsp.internal.netty.pipeline.BossHandler;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.PingCalculator;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ChannelWrapper {
    @Setter
    private int port;
    private io.netty.channel.Channel channel;
    private BossHandler handler;
    @Getter
    private PingCalculator pingCalculator = new PingCalculator();
    private CallBackContainer callBackContainer = new CallBackContainer();
    public ChannelWrapper(BossHandler handler, Channel channel){
        this.handler = handler;
        this.channel = channel;
    }
    public ChannelWrapper(BossHandler handler, Channel channel, int port){
        this.handler = handler;
        this.channel = channel;
        this.port = port;
    }
    public<T extends AbstractPacket> void startCallBack(AbstractPacket toSend, Class<T> type, PacketCallBack<T> onRecieved){
        callBackContainer.addOnQueue(this, toSend, type, onRecieved);
    }
    public void sendPacket(AbstractPacket packet){
        if(handler.getConnectionState() != ConnectionState.CONNECTED)
        {
            BCSPLogManager.getLogger().err("Some logic tried to send packet before connection established or disconnected. (Packet: {0})", packet.getClass().getSimpleName());
            return;
        }
        channel.writeAndFlush(packet);
    }
}

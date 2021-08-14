package kr.msleague.bcsp.bukkit;

import kr.msleague.all.MSPlugin;
import kr.msleague.bcsp.bukkit.event.connection.BCSPConnectedEvent;
import kr.msleague.bcsp.internal.GlobalProperties;
import kr.msleague.bcsp.internal.ServerType;
import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.logger.JavaUtilLogger;
import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.sys.HandShakePacket;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BCSPBootstrapBukkit extends MSPlugin {
    public BCSPBootstrapBukkit(){
        GlobalProperties.setType(ServerType.BUKKIT);
    }
    @Getter
    private static boolean active;
    public static BCSPBootstrapBukkit INSTANCE;
    @Getter
    private static long authCodeA, authCodeB;
    @Getter
    private ChannelWrapper channelWrapper;
    @Override
    public void onEnable(){
        INSTANCE = this;
        active = true;
        new BCSPBukkitAPI(this);
        BCSPLogManager.setLogger(new JavaUtilLogger(getLogger()));
        GlobalProperties.loadProperties(getDataFolder(), list->{
            list.add("netty.targetServer.address=localhost");
            list.add("netty.targetServer.port=9090");
            list.add("handshake.id.a=123901248128");
            list.add("handshake.id.b=436894693468");
        });
        authCodeA = Long.parseLong(GlobalProperties.getProperties("handshake.id.a"));
        authCodeB = Long.parseLong(GlobalProperties.getProperties("handshake.id.b"));
        Direction.INBOUND.addListener(HandShakePacket.class, (pack, wrap)->{
            if(pack.getAuthCodeA() == authCodeA && pack.getAuthCodeB() == authCodeB){
                wrap.getHandler().setConnectionState(ConnectionState.CONNECTED);
                channelWrapper = wrap.getHandler().getWrapper();
                getServer().getScheduler().runTask(getPlugin(), ()->getServer().getPluginManager().callEvent(new BCSPConnectedEvent(wrap, wrap.getHandler().getConnectionState())));
            }else{
                BCSPLogManager.getLogger().err("BCSP Failed to handshake. Auth Code incorrect.");
                wrap.getChannel().disconnect();
            }
        });
        new ChannelConnecterThread().run();
    }
    @Override
    public void onDisable(){
        active = false;
        if(channelWrapper!=null && channelWrapper.getChannel().isOpen() && channelWrapper.getChannel().isActive()){
            channelWrapper.getChannel().disconnect();
        }
    }
}

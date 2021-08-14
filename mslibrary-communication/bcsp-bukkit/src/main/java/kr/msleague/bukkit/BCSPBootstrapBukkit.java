package kr.msleague.bukkit;

import kr.msleague.bukkit.event.connection.BCSPConnectedEvent;
import kr.msleague.internal.GlobalProperties;
import kr.msleague.internal.ServerType;
import kr.msleague.internal.logger.BCSPLogManager;
import kr.msleague.internal.logger.JavaUtilLogger;
import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.packet.Direction;
import kr.msleague.internal.netty.packet.sys.HandShakePacket;
import kr.msleague.internal.netty.pipeline.ConnectionState;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BCSPBootstrapBukkit extends JavaPlugin {
    public BCSPBootstrapBukkit(){
        GlobalProperties.setType(ServerType.BUKKIT);
    }
    public static BCSPBootstrapBukkit INSTANCE;
    @Getter
    private static long authCodeA, authCodeB;
    @Getter
    private ChannelWrapper channelWrapper;
    @Override
    public void onEnable(){
        INSTANCE = this;
        new BCSPBukkitAPI(this);
        BCSPLogManager.setLogger(new JavaUtilLogger(getLogger()));
        GlobalProperties.loadProperties(getDataFolder(), list->{
            list.add("netty.server.address=localhost");
            list.add("netty.server.port=9090");
            list.add("handshake.id.a=123901248128");
            list.add("handshake.id.b=436894693468");
        });
        authCodeA = Long.parseLong(GlobalProperties.getProperties("handshake.id.a"));
        authCodeB = Long.parseLong(GlobalProperties.getProperties("handshake.id.b"));
        Direction.INBOUND.addListener(HandShakePacket.class, (pack, wrap)->{
            if(pack.getAuthCodeA() == authCodeA && pack.getAuthCodeB() == authCodeB){
                wrap.getHandler().setConnectionState(ConnectionState.CONNECTED);
                channelWrapper = wrap.getHandler().getWrapper();
                getServer().getScheduler().runTask(this, ()->getServer().getPluginManager().callEvent(new BCSPConnectedEvent(wrap, wrap.getHandler().getConnectionState())));
            }else{
                BCSPLogManager.getLogger().err("BCSP Failed to handshake. Auth Code incorrect.");
                wrap.getChannel().disconnect();
            }
        });
        new ChannelConnecterThread().run();
    }
}

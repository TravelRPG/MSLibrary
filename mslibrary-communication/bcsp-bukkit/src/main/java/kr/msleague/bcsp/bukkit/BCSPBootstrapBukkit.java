package kr.msleague.bcsp.bukkit;

import kr.msleague.bcsp.bukkit.event.connection.BCSPConnectedEvent;
import kr.msleague.bcsp.internal.GlobalProperties;
import kr.msleague.bcsp.internal.ServerType;
import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.logger.JavaUtilLogger;
import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.sys.HandShakePacket;
import kr.msleague.bcsp.internal.netty.packet.sys.ShutdownPacket;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import lombok.Getter;
import org.bukkit.Bukkit;

@LoadPriority(priority = -999)
public class BCSPBootstrapBukkit extends MSPlugin {
    public static BCSPBootstrapBukkit INSTANCE;
    @Getter
    private static boolean active;
    @Getter
    private static long authCodeA, authCodeB;
    @Getter
    private ChannelWrapper channelWrapper;
    public BCSPBootstrapBukkit() {
        GlobalProperties.setType(ServerType.BUKKIT);
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        active = true;
        new BCSPBukkitAPI(this);
        BCSPLogManager.setLogger(new JavaUtilLogger(getLogger()));
        GlobalProperties.loadProperties(getDataFolder(), list -> {
            list.add("netty.enabled=true");
            list.add("netty.targetServer.address=localhost");
            list.add("netty.targetServer.port=9090");
            list.add("handshake.id.a=123901248128");
            list.add("handshake.id.b=436894693468");
        });
        authCodeA = Long.parseLong(GlobalProperties.getProperties("handshake.id.a"));
        authCodeB = Long.parseLong(GlobalProperties.getProperties("handshake.id.b"));
        ChannelConnecterThread.setEnabled(Boolean.parseBoolean(GlobalProperties.getProperties("netty.enabled", "true")));
        Direction.INBOUND.addListener(HandShakePacket.class, (pack, wrap) -> {
            if (pack.getAuthCodeA() == authCodeA && pack.getAuthCodeB() == authCodeB) {
                wrap.getHandler().setConnectionState(ConnectionState.CONNECTED);
                channelWrapper = wrap.getHandler().getWrapper();
                getServer().getScheduler().runTask(getPlugin(), () -> getServer().getPluginManager().callEvent(new BCSPConnectedEvent(wrap, wrap.getHandler().getConnectionState())));
            } else {
                BCSPLogManager.getLogger().err("BCSP Failed to handshake. Auth Code incorrect.");
                wrap.getChannel().disconnect();
            }
        });
        Direction.INBOUND.addListener(ShutdownPacket.class, (pack, wrap) -> {
            if (pack.isShutdownTarget()) {
                wrap.setEnabled(false);
                ChannelConnecterThread.setEnabled(false);
                BCSPLogManager.getLogger().info("BungeeCord(Proxy) requested shutdown. Shutting down server...");
                Bukkit.shutdown();
            } else {
                wrap.setEnabled(false);
                ChannelConnecterThread.setWaitTime(15);
                BCSPLogManager.getLogger().info("BungeeCord(Proxy) executed shutdown procedure. AutoReconnect period adjusted to 15 seconds.");
            }
        });
        new ChannelConnecterThread().run();
    }

    @Override
    public void onDisable() {
        active = false;
        if (channelWrapper != null && channelWrapper.getChannel().isOpen() && channelWrapper.getChannel().isActive()) {
            channelWrapper.getChannel().disconnect();
        }
    }
}

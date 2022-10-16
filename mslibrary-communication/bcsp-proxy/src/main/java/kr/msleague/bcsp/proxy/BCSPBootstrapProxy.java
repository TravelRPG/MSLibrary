package kr.msleague.bcsp.proxy;

import io.netty.channel.Channel;
import kr.msleague.bcsp.internal.BungeeComsoServerBootStrap;
import kr.msleague.bcsp.internal.GlobalProperties;
import kr.msleague.bcsp.internal.ServerType;
import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.logger.JavaUtilLogger;
import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.sys.HandShakePacket;
import kr.msleague.bcsp.internal.netty.packet.sys.RelayingPacket;
import kr.msleague.bcsp.internal.netty.packet.sys.RelayingResult;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import kr.msleague.bcsp.internal.netty.pipeline.TimeOutHandler;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@LoadPriority(priority = -999)
public class BCSPBootstrapProxy extends Plugin {
    protected final BCSPProxyChannelContainer channelContainer = new BCSPProxyChannelContainer();
    private long authCodeA, authCodeB;
    public BCSPBootstrapProxy() {
        GlobalProperties.setType(ServerType.BUNGEECORD);
    }

    @Override
    public void onEnable() {
        BCSPLogManager.setLogger(new JavaUtilLogger(getLogger()));
        BCSPProxyAPI.setPluginInstance(this);
        GlobalProperties.loadProperties(getDataFolder(), list -> {
            list.add("netty.server.address=localhost");
            list.add("netty.server.port=9090");
            list.add("handshake.id.a=123901248128");
            list.add("handshake.id.b=436894693468");
            list.add("bcsp.shutdownOnDisable=true");
        });
        this.authCodeA = Long.parseLong(GlobalProperties.getProperties("handshake.id.a"));
        this.authCodeB = Long.parseLong(GlobalProperties.getProperties("handshake.id.b"));
        initializeServer();
        registerDefaultListeners();

    }
    private void initializeServer(){
        CompletableFuture<Channel> future = BungeeComsoServerBootStrap.initServer();
        future.whenCompleteAsync((channel, throwable) -> {
            if (throwable != null) {
                BCSPLogManager.getLogger().err("BCSP Failed to bootup successfully. Check following stack trace");
                throwable.printStackTrace();
                return;
            }
            BCSPLogManager.getLogger().info("BSCP Server Initialization Success!");
        });
    }

    private void registerDefaultListeners(){
        Direction.INBOUND.addListener(HandShakePacket.class, (pack, wrap) -> {
            if (pack.getAuthCodeA() == authCodeA && pack.getAuthCodeB() == authCodeB) {
                wrap.setPort(pack.getServerPort());
                wrap.getHandler().setConnectionState(ConnectionState.CONNECTED);
                wrap.getChannel().writeAndFlush(new HandShakePacket(authCodeA, authCodeB, -1));
                channelContainer.onChannelActive(pack.getServerPort(), wrap);
                new PingPongManagementThread(wrap).start();
                wrap.getChannel().pipeline().addFirst("timeout-handler", new TimeOutHandler(5L, TimeUnit.SECONDS));
            } else {
                BCSPLogManager.getLogger().err("BCSP Failed to handshake. Auth Code incorrect. (Address: {0})", wrap.getChannel().remoteAddress());
                wrap.getChannel().disconnect();
            }
        });
        Direction.INBOUND.addListener(RelayingPacket.class, (packet, wrapper) -> {
            try {
                int port = Integer.parseInt(packet.getTargetServer());
                if (port == -1) {
                    channelContainer.portChannelContainer.values().forEach(chan -> chan.sendPacket(new RelayingResult(packet.getRelay())));
                } else {
                    ChannelWrapper target = channelContainer.getChannelByPort(port);
                    if (target == null) {
                        BCSPLogManager.getLogger().err("Relaying packet failed due to Target Server Offline! (Server: {0})", packet.getTargetServer());
                        return;
                    }
                    target.sendPacket(new RelayingResult(packet.getRelay()));
                }
            } catch (NumberFormatException ex) {
                ChannelWrapper target = channelContainer.getChannelByServerName(packet.getTargetServer());
                if (target == null) {
                    BCSPLogManager.getLogger().err("Relaying packet failed due to Target Server Offline! (Server: {0})", packet.getTargetServer());
                    return;
                }
                target.sendPacket(new RelayingResult(packet.getRelay()));
            }

        });
    }

    @Override
    public void onDisable() {
        channelContainer.onShutdown();
    }
}

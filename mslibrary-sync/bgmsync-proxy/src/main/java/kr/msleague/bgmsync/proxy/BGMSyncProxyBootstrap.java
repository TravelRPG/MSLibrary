package kr.msleague.bgmsync.proxy;

import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.bgmsync.global.packet.ProxyJoinPacket;
import kr.msleague.bgmsync.global.packet.ProxyQuitPacket;
import kr.msleague.bgmsync.global.packet.SyncRequestPacket;
import kr.msleague.bgmsync.proxy.listener.JoinQuitListener;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

@LoadPriority(priority = 990)
public class BGMSyncProxyBootstrap extends Plugin {
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new JoinQuitListener());
        BCSPProxyAPI.getInst().registerOuterPacket(0x9005, ProxyJoinPacket.class, ProxyJoinPacket::new);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9006, ProxyQuitPacket.class, ProxyQuitPacket::new);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9007, SyncRequestPacket.class, SyncRequestPacket::new);
        BCSPProxyAPI.getInst().registerInnerPacket(0x9007, SyncRequestPacket.class, SyncRequestPacket::new, (pack, wrap) -> {
            SyncRequestPacket rpack = new SyncRequestPacket(pack.getSyncClass(), pack.getFromPort(), pack.getUuid(), buf -> buf.writeBytes(pack.getBuf()));
            ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(pack.getUuid());
            if (pp == null || pp.getServer() == null) {
                return;
            }
            int target = pp.getServer().getAddress().getPort();
            if (target == pack.getFromPort()) {
                BCSPLogManager.getLogger().err("BGMSync detected sync request's target is same server. is it called from Quit? {0}", pack.getUuid());
                return;
            }
            ChannelWrapper wrapper = BCSPProxyAPI.Unsafe.getChannelContainer().getChannelByPort(target);
            if (wrapper != null)
                wrapper.sendPacket(rpack);
        });
    }
}

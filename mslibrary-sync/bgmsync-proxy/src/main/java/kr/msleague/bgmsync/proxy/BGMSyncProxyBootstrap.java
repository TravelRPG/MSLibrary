package kr.msleague.bgmsync.proxy;

import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.bgmsync.global.packet.ProxyJoinPacket;
import kr.msleague.bgmsync.global.packet.ProxyQuitPacket;
import kr.msleague.bgmsync.global.packet.SyncRequestPacket;
import kr.msleague.bgmsync.proxy.listener.JoinQuitListener;
import net.md_5.bungee.api.plugin.Plugin;

public class BGMSyncProxyBootstrap extends Plugin {
    public void onEnable(){
        getProxy().getPluginManager().registerListener(this, new JoinQuitListener());
        BCSPProxyAPI.getInst().registerOuterPacket(0x9005, ProxyJoinPacket.class);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9006, ProxyQuitPacket.class);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9007, SyncRequestPacket.class);
        BCSPProxyAPI.getInst().registerInnerPacket(0x9007, SyncRequestPacket.class, (pack, wrap)->{
            new SyncRequestPacket(pack.getFromPort(), pack.getUuid(), buf->buf.writeByte())
        });
    }
}

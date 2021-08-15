package kr.msleague.bgmsync.proxy.listener;

import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.bgmsync.global.packet.ProxyJoinPacket;
import kr.msleague.bgmsync.global.packet.ProxyQuitPacket;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashSet;

public class JoinQuitListener implements Listener {
    private HashSet<ProxiedPlayer> proxyJoin = new HashSet<>();

    @EventHandler
    public void onServerTry(ServerConnectEvent event){
        if(event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY || event.getReason() == ServerConnectEvent.Reason.LOBBY_FALLBACK){
            proxyJoin.add(event.getPlayer());
        }
    }
    @EventHandler
    public void onJoin(ServerConnectedEvent event){
        if(event.getServer()!=null && proxyJoin.remove(event.getPlayer())){
            int port = event.getServer().getAddress().getPort();
            BCSPProxyAPI.getInst().sendPacketToSpecificServers(port, new ProxyJoinPacket(event.getPlayer().getUniqueId()));
        }
    }
    @EventHandler
    public void onQuit(PlayerDisconnectEvent event){
        if(event.getPlayer().getServer() == null)
            return;
        int port = event.getPlayer().getServer().getAddress().getPort();
        BCSPProxyAPI.getInst().sendPacketToSpecificServers(port, new ProxyQuitPacket(event.getPlayer().getUniqueId()));
    }
}

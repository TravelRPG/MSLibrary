package kr.msleague.teleport.proxy;

import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.teleport.global.GLocation;
import kr.msleague.teleport.global.ITeleporter;
import kr.msleague.teleport.global.packet.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class ProxyTeleporter implements ITeleporter {
    @Override
    public void teleport(UUID from, UUID to) {
        ProxiedPlayer a = ProxyServer.getInstance().getPlayer(from);
        ProxiedPlayer b = ProxyServer.getInstance().getPlayer(to);
        if(a == null || b == null || !a.isConnected() || !b.isConnected())
            return;
        ServerInfo si = b.getServer().getInfo();
        BCSPProxyAPI.getInst().sendPacketToSpecificServers(si.getAddress().getPort(), new PlayerTeleportPacket(from, to));
        if(!a.getServer().getInfo().equals(si)){
            a.connect(si);
        }
    }

    @Override
    public void teleport(UUID who, GLocation to) {
        int port;
        ServerInfo si = null;
        try{
            port = Integer.parseInt(to.getServerName());
            for (ServerInfo value : ProxyServer.getInstance().getServers().values()) {
                if(value.getAddress().getPort() == port){
                    si = value;
                    break;
                }
            }
        }catch(NumberFormatException ex){
            if(to.getServerName().startsWith("optimal.")){
                String serverPrefix = to.getServerName().split("\\.")[1];
                int lowest = Integer.MAX_VALUE;
                for (ServerInfo value : ProxyServer.getInstance().getServers().values()) {
                    int player = value.getPlayers().size();
                    if(value.getName().startsWith(serverPrefix) && lowest > player){
                        si = value;
                        lowest = player;
                    }
                }
                if(si == null)
                    return;
                port = si.getAddress().getPort();
            }else{
                si = ProxyServer.getInstance().getServerInfo(to.getServerName());
                if(si == null)
                    return;
                port = si.getAddress().getPort();
            }
        }
        ProxiedPlayer a = ProxyServer.getInstance().getPlayer(who);
        if(a == null || !a.isConnected() || si == null){
            return;
        }
        BCSPProxyAPI.getInst().sendPacketToSpecificServers(port, new LocationTeleportPacket(who, to));
        if(!a.getServer().getInfo().equals(si)){
            a.connect(si);
        }
    }

    @Override
    public void teleport(String who, String to) {
        ProxiedPlayer a = ProxyServer.getInstance().getPlayer(who);
        ProxiedPlayer b = ProxyServer.getInstance().getPlayer(to);
        if(a == null || b == null || !a.isConnected() || !b.isConnected())
        {
            return;
        }
        ServerInfo si = b.getServer().getInfo();
        BCSPProxyAPI.getInst().sendPacketToSpecificServers(si.getAddress().getPort(), new PlayerNameTeleportPacket(who, to));
        if(!a.getServer().getInfo().equals(si)){
            a.connect(si);
        }
    }

    @Override
    public void teleport(String who, GLocation to) {
        int port;
        ServerInfo si = null;
        try{
            port = Integer.parseInt(to.getServerName());
            for (ServerInfo value : ProxyServer.getInstance().getServers().values()) {
                if(value.getAddress().getPort() == port){
                    si = value;
                    break;
                }
            }
        }catch(NumberFormatException ex){
            if(to.getServerName().startsWith("optimal.")){
                String serverPrefix = to.getServerName().split("\\.")[1];
                int lowest = Integer.MAX_VALUE;
                for (ServerInfo value : ProxyServer.getInstance().getServers().values()) {
                    int player = value.getPlayers().size();
                    if(value.getName().startsWith(serverPrefix) && lowest > player){
                        si = value;
                        lowest = player;
                    }
                }
                if(si == null)
                    return;
                port = si.getAddress().getPort();
            }else{
                si = ProxyServer.getInstance().getServerInfo(to.getServerName());
                if(si == null)
                    return;
                port = si.getAddress().getPort();
            }
        }
        ProxiedPlayer a = ProxyServer.getInstance().getPlayer(who);
        if(a == null || !a.isConnected() || si == null){
            return;
        }
        BCSPProxyAPI.getInst().sendPacketToSpecificServers(port, new NamedLocationTeleportPacket(who, to));
        if(!a.getServer().getInfo().equals(si)){
            a.connect(si);
        }
    }
}

package kr.msleague.channel;

import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.channel.packet.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class MSChannelProxyInitializer extends Plugin {
    public static ServerInfo getServerInfoByString(String name) {
        try {
            int i = Integer.parseInt(name);
            for (ServerInfo si : ProxyServer.getInstance().getServers().values()) {
                if (si.getAddress().getPort() == i)
                    return si;
            }
        } catch (NumberFormatException ex) {
            return ProxyServer.getInstance().getServerInfo(name);
        }
        return null;
    }

    public void onEnable() {
        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9500, PacketIsServerExists.class, PacketIsServerExists::new, packet -> new PacketIsServerExists.Result(getServerInfoByString(packet.getServer()) != null));
        BCSPProxyAPI.getInst().registerOuterPacket(0x9501, PacketIsServerExists.Result.class, PacketIsServerExists.Result::new);

        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9502, PacketIsServerOnline.class, PacketIsServerOnline::new, packet -> {
            try {
                int port = Integer.parseInt(packet.getServer());
                return new PacketIsServerOnline.Result(BCSPProxyAPI.Unsafe.getChannelContainer().getChannelByPort(port) != null);
            } catch (NumberFormatException ex) {
                return new PacketIsServerOnline.Result(BCSPProxyAPI.Unsafe.getChannelContainer().getChannelByServerName(packet.getServer()) != null);
            }
        });
        BCSPProxyAPI.getInst().registerOuterPacket(0x9503, PacketIsServerOnline.Result.class, PacketIsServerOnline.Result::new);

        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9504, PacketGetServerOnlineCount.class, PacketGetServerOnlineCount::new, packet -> {
            if (packet.getServer().equals("-1")) {
                return new PacketGetServerOnlineCount.Result(ProxyServer.getInstance().getOnlineCount());
            } else {
                ServerInfo info = getServerInfoByString(packet.getServer());
                if (info != null)
                    return new PacketGetServerOnlineCount.Result(info.getPlayers().size());
                else
                    return new PacketGetServerOnlineCount.Result(0);
            }
        });
        BCSPProxyAPI.getInst().registerOuterPacket(0x9505, PacketGetServerOnlineCount.Result.class, PacketGetServerOnlineCount.Result::new);

        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9506, PacketGetServerUuids.class, PacketGetServerUuids::new, packet -> {
            if (packet.getServer().equals("-1")) {
                return new PacketGetServerUuids.Result(ProxyServer.getInstance().getPlayers().stream().map(ProxiedPlayer::getUniqueId).collect(Collectors.toList()));
            } else {
                ServerInfo info = getServerInfoByString(packet.getServer());
                if (info != null)
                    return new PacketGetServerUuids.Result(info.getPlayers().stream().map(ProxiedPlayer::getUniqueId).collect(Collectors.toList()));
                else
                    return new PacketGetServerUuids.Result(Collections.emptyList());
            }
        });
        BCSPProxyAPI.getInst().registerOuterPacket(0x9507, PacketGetServerUuids.Result.class, PacketGetServerUuids.Result::new);

        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9508, PacketGetServerUsernames.class, PacketGetServerUsernames::new, packet -> {
            if (packet.getServer().equals("-1")) {
                return new PacketGetServerUsernames.Result(ProxyServer.getInstance().getPlayers().stream().map(ProxiedPlayer::getName).collect(Collectors.toList()));
            } else {
                ServerInfo info = getServerInfoByString(packet.getServer());
                if (info != null)
                    return new PacketGetServerUsernames.Result(info.getPlayers().stream().map(ProxiedPlayer::getName).collect(Collectors.toList()));
                else
                    return new PacketGetServerUsernames.Result(Collections.emptyList());
            }
        });
        BCSPProxyAPI.getInst().registerOuterPacket(0x9509, PacketGetServerUsernames.Result.class, PacketGetServerUsernames.Result::new);

        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9510, PacketIsUserOnline.class, PacketIsUserOnline::new, packet -> {
            try {
                UUID uuid = UUID.fromString(packet.getUsername());
                return new PacketIsUserOnline.Result(ProxyServer.getInstance().getPlayer(uuid) != null);
            } catch (Exception ex) {
                return new PacketIsUserOnline.Result(ProxyServer.getInstance().getPlayer(packet.getUsername()) != null);
            }
        });
        BCSPProxyAPI.getInst().registerOuterPacket(0x9511, PacketIsUserOnline.Result.class, PacketIsUserOnline.Result::new);

        BCSPProxyAPI.getInst().registerCallBackProcessor(0x9512, PacketGetUserServer.class, PacketGetUserServer::new, packet -> {
            try {
                UUID uuid = UUID.fromString(packet.getUsername());
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(uuid);
                if (pp == null || pp.getServer() == null)
                    return new PacketGetUserServer.Result("null");
                else
                    return new PacketGetUserServer.Result(pp.getServer().getInfo().getName());
            } catch (Exception ex) {
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(packet.getUsername());
                if (pp == null || pp.getServer() == null)
                    return new PacketGetUserServer.Result("null");
                else
                    return new PacketGetUserServer.Result(pp.getServer().getInfo().getName());
            }
        });
        BCSPProxyAPI.getInst().registerOuterPacket(0x9513, PacketGetUserServer.Result.class, PacketGetUserServer.Result::new);
    }

}

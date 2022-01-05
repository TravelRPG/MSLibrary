package kr.msleague.teleport.proxy;

import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.teleport.global.Teleporter;
import kr.msleague.teleport.global.packet.LocationTeleportPacket;
import kr.msleague.teleport.global.packet.NamedLocationTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerNameTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerTeleportPacket;
import net.md_5.bungee.api.plugin.Plugin;

public class TeleporterProxyBootstrap extends Plugin {
    public void onEnable() {
        Teleporter.setTeleporter(new ProxyTeleporter());
        BCSPProxyAPI.getInst().registerOuterPacket(0x9010, LocationTeleportPacket.class, LocationTeleportPacket::new);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9011, NamedLocationTeleportPacket.class, NamedLocationTeleportPacket::new);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9012, PlayerNameTeleportPacket.class, PlayerNameTeleportPacket::new);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9013, PlayerTeleportPacket.class, PlayerTeleportPacket::new);

        BCSPProxyAPI.getInst().registerInnerPacket(0x9010, LocationTeleportPacket.class, LocationTeleportPacket::new, (pack, wrap) -> Teleporter.teleport(pack.getPlayer(), pack.getLocation()));
        BCSPProxyAPI.getInst().registerInnerPacket(0x9011, NamedLocationTeleportPacket.class, NamedLocationTeleportPacket::new, (pack, wrap) -> Teleporter.teleport(pack.getPlayer(), pack.getLocation()));
        BCSPProxyAPI.getInst().registerInnerPacket(0x9012, PlayerNameTeleportPacket.class, PlayerNameTeleportPacket::new, (pack, wrap) -> Teleporter.teleport(pack.getPlayer(), pack.getLocation()));
        BCSPProxyAPI.getInst().registerInnerPacket(0x9013, PlayerTeleportPacket.class, PlayerTeleportPacket::new, (pack, wrap) -> Teleporter.teleport(pack.getPlayer(), pack.getLocation()));
    }
}

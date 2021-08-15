package kr.msleague.teleport.bukkit;

import kr.msleague.bcsp.proxy.BCSPProxyAPI;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.teleport.global.Teleporter;
import kr.msleague.teleport.global.packet.LocationTeleportPacket;
import kr.msleague.teleport.global.packet.NamedLocationTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerNameTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerTeleportPacket;

public class TeleporterBukkitBootstrap extends MSPlugin {
    public void onEnable(){
        Teleporter.setTeleporter(new BukkitTeleporter());
        BCSPProxyAPI.getInst().registerOuterPacket(0x9010, LocationTeleportPacket.class);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9011, NamedLocationTeleportPacket.class);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9012, PlayerNameTeleportPacket.class);
        BCSPProxyAPI.getInst().registerOuterPacket(0x9013, PlayerTeleportPacket.class);

        BCSPProxyAPI.getInst().registerInnerPacket(0x9010, LocationTeleportPacket.class, (pack, wrap)->BukkitPacketListener.onRecievedLTP(null, pack));
        BCSPProxyAPI.getInst().registerInnerPacket(0x9011, NamedLocationTeleportPacket.class, (pack, wrap)->BukkitPacketListener.onRecievedNLTP(null, pack));
        BCSPProxyAPI.getInst().registerInnerPacket(0x9012, PlayerNameTeleportPacket.class, (pack, wrap)->BukkitPacketListener.onRecievedPNTP(null, pack));
        BCSPProxyAPI.getInst().registerInnerPacket(0x9013, PlayerTeleportPacket.class, (pack, wrap)->BukkitPacketListener.onRecievedPTP(null, pack));
    }
}

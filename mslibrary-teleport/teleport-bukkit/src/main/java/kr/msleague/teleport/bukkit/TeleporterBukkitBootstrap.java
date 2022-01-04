package kr.msleague.teleport.bukkit;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import kr.msleague.teleport.global.Teleporter;
import kr.msleague.teleport.global.packet.LocationTeleportPacket;
import kr.msleague.teleport.global.packet.NamedLocationTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerNameTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerTeleportPacket;

@LoadPriority(priority = 10)
public class TeleporterBukkitBootstrap extends MSPlugin {
    public void onEnable() {
        Teleporter.setTeleporter(new BukkitTeleporter());
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9010, LocationTeleportPacket.class, LocationTeleportPacket::new);
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9011, NamedLocationTeleportPacket.class, NamedLocationTeleportPacket::new);
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9012, PlayerNameTeleportPacket.class, PlayerNameTeleportPacket::new);
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9013, PlayerTeleportPacket.class, PlayerTeleportPacket::new);

        BCSPBukkitAPI.getInst().registerInnerPacket(0x9010, LocationTeleportPacket.class, LocationTeleportPacket::new, (pack, wrap) -> BukkitPacketListener.onRecievedLTP(null, pack));
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9011, NamedLocationTeleportPacket.class, NamedLocationTeleportPacket::new, (pack, wrap) -> BukkitPacketListener.onRecievedNLTP(null, pack));
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9012, PlayerNameTeleportPacket.class, PlayerNameTeleportPacket::new, (pack, wrap) -> BukkitPacketListener.onRecievedPNTP(null, pack));
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9013, PlayerTeleportPacket.class, PlayerTeleportPacket::new, (pack, wrap) -> BukkitPacketListener.onRecievedPTP(null, pack));
    }
}

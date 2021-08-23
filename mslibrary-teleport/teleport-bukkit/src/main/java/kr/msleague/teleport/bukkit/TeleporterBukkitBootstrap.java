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
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9010, LocationTeleportPacket.class);
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9011, NamedLocationTeleportPacket.class);
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9012, PlayerNameTeleportPacket.class);
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9013, PlayerTeleportPacket.class);

        BCSPBukkitAPI.getInst().registerInnerPacket(0x9010, LocationTeleportPacket.class, (pack, wrap) -> BukkitPacketListener.onRecievedLTP(null, pack));
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9011, NamedLocationTeleportPacket.class, (pack, wrap) -> BukkitPacketListener.onRecievedNLTP(null, pack));
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9012, PlayerNameTeleportPacket.class, (pack, wrap) -> BukkitPacketListener.onRecievedPNTP(null, pack));
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9013, PlayerTeleportPacket.class, (pack, wrap) -> BukkitPacketListener.onRecievedPTP(null, pack));
    }
}

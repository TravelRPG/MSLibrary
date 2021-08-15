package kr.msleague.teleport.bukkit;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.teleport.global.GLocation;
import kr.msleague.teleport.global.ITeleporter;
import kr.msleague.teleport.global.packet.*;

import java.util.UUID;

public class BukkitTeleporter implements ITeleporter {
    @Override
    public void teleport(UUID from, UUID to) {
        PlayerTeleportPacket packet = new PlayerTeleportPacket(from, to);
        BCSPBukkitAPI.getInst().sendPacketToProxy(packet);
    }

    @Override
    public void teleport(UUID who, GLocation to) {
        LocationTeleportPacket packet = new LocationTeleportPacket(who, to);
        BCSPBukkitAPI.getInst().sendPacketToProxy(packet);
    }

    @Override
    public void teleport(String who, String to) {
        PlayerNameTeleportPacket packet = new PlayerNameTeleportPacket(who, to);
        BCSPBukkitAPI.getInst().sendPacketToProxy(packet);
    }

    @Override
    public void teleport(String who, GLocation to) {
        NamedLocationTeleportPacket packet = new NamedLocationTeleportPacket(who, to);
        BCSPBukkitAPI.getInst().sendPacketToProxy(packet);
    }
}

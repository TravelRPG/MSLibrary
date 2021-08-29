package kr.msleague.teleport.bukkit;

import io.netty.channel.Channel;
import kr.msleague.teleport.bukkit.task.WaitUntilLoginNamedTask;
import kr.msleague.teleport.bukkit.task.WaitUntilLoginTask;
import kr.msleague.teleport.global.packet.LocationTeleportPacket;
import kr.msleague.teleport.global.packet.NamedLocationTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerNameTeleportPacket;
import kr.msleague.teleport.global.packet.PlayerTeleportPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitPacketListener {
    public static void onRecievedLTP(Channel channel, LocationTeleportPacket ltp) {
        new WaitUntilLoginTask(ltp.getPlayer(), p -> {
            if (ltp.getLocation().getY() != -255)
                p.teleport(GLocationAdapter.asBukkitLocation(ltp.getLocation()));
        });
    }

    public static void onRecievedPTP(Channel channel, PlayerTeleportPacket ptp) {
        Player target = Bukkit.getPlayer(ptp.getLocation());
        if (target.isOnline()) {
            new WaitUntilLoginTask(ptp.getPlayer(), p -> {
                p.teleport(target.getLocation());
            });
        }
    }

    public static void onRecievedPNTP(Channel channel, PlayerNameTeleportPacket ptp) {
        Player target = Bukkit.getPlayer(ptp.getLocation());
        if (target.isOnline()) {
            new WaitUntilLoginNamedTask(ptp.getPlayer(), p -> {
                p.teleport(target.getLocation());
            });
        }
    }

    public static void onRecievedNLTP(Channel channel, NamedLocationTeleportPacket nltp) {
        new WaitUntilLoginNamedTask(nltp.getPlayer(), p -> {
            if (nltp.getLocation().getY() != -255)
                p.teleport(GLocationAdapter.asBukkitLocation(nltp.getLocation()));
        });
    }
}

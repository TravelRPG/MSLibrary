package kr.msleague.teleport.bukkit;

import kr.msleague.teleport.global.GLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class GLocationAdapter {
    public static Location asBukkitLocation(GLocation loc) {
        return new Location(Bukkit.getWorld(loc.getWorldName()), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public static GLocation fromBukkitLocation(Location loc, String servername) {
        return new GLocation(servername, loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}

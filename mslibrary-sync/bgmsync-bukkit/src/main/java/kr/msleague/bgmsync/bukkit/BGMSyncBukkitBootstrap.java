package kr.msleague.bgmsync.bukkit;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bgmsync.bukkit.event.PlayerProxyJoinEvent;
import kr.msleague.bgmsync.bukkit.event.PlayerProxyQuitEvent;
import kr.msleague.bgmsync.global.packet.ProxyJoinPacket;
import kr.msleague.bgmsync.global.packet.ProxyQuitPacket;
import kr.msleague.bootstrap.MSPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BGMSyncBukkitBootstrap extends MSPlugin implements Listener {
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, getPlugin());
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9005, ProxyJoinPacket.class, (pack,chan)->{
            Bukkit.getScheduler().runTask(getPlugin(), ()->Bukkit.getPluginManager().callEvent(new PlayerProxyJoinEvent(pack.getUuid())));
        });
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9006, ProxyQuitPacket.class, (pack, chan)->{
            Bukkit.getScheduler().runTask(getPlugin(), ()->Bukkit.getPluginManager().callEvent(new PlayerProxyQuitEvent(pack.getUuid())));
        });
    }
    public void onDisable(){

    }
}

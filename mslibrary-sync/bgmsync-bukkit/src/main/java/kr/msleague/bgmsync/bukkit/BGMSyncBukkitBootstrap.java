package kr.msleague.bgmsync.bukkit;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import kr.msleague.bgmsync.bukkit.event.PlayerProxyJoinEvent;
import kr.msleague.bgmsync.bukkit.event.PlayerProxyQuitEvent;
import kr.msleague.bgmsync.bukkit.event.PlayerSyncReceivedEvent;
import kr.msleague.bgmsync.global.packet.ProxyJoinPacket;
import kr.msleague.bgmsync.global.packet.ProxyQuitPacket;
import kr.msleague.bgmsync.global.packet.SyncRequestPacket;
import kr.msleague.bootstrap.MSPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

public class BGMSyncBukkitBootstrap extends MSPlugin implements Listener {
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, getPlugin());
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9005, ProxyJoinPacket.class, (pack,chan)->{
            Bukkit.getScheduler().runTask(getPlugin(), ()->Bukkit.getPluginManager().callEvent(new PlayerProxyJoinEvent(pack.getUuid())));
        });
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9006, ProxyQuitPacket.class, (pack, chan)->{
            Bukkit.getScheduler().runTask(getPlugin(), ()->Bukkit.getPluginManager().callEvent(new PlayerProxyQuitEvent(pack.getUuid())));
        });
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9007, SyncRequestPacket.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9007, SyncRequestPacket.class, (pack, chan)->{
            PlayerSyncReceivedEvent ev = new PlayerSyncReceivedEvent(pack.getUuid(), pack.getSyncClass(),pack.getBuf());
            getServer().getPluginManager().callEvent(ev);
            ev.release();
        });
    }
    public void onDisable(){

    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        BGMSyncAPI.sendRequest(BGMSyncBukkitBootstrap.class, event.getPlayer().getUniqueId(), buf->{
            ByteBufUtility.writeString("Umunsdjfsdfjs", buf);
        });
    }
    @EventHandler
    public void onRec(PlayerSyncReceivedEvent event){
        if(event.isSyncOf(BGMSyncBukkitBootstrap.class)){
            String msg = ByteBufUtility.readString(event.getBuffer());
            System.out.println(msg);
        }
    }
}

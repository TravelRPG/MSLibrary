package kr.msleague.bgmsync.bukkit;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bgmsync.bukkit.event.PlayerProxyJoinEvent;
import kr.msleague.bgmsync.bukkit.event.PlayerProxyQuitEvent;
import kr.msleague.bgmsync.bukkit.event.PlayerSyncReceivedEvent;
import kr.msleague.bgmsync.global.packet.ProxyJoinPacket;
import kr.msleague.bgmsync.global.packet.ProxyQuitPacket;
import kr.msleague.bgmsync.global.packet.SyncRequestPacket;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import org.bukkit.Bukkit;

@LoadPriority(priority = 990)
public class BGMSyncBukkitBootstrap extends MSPlugin {
    public void onEnable() {
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9005, ProxyJoinPacket.class, (pack, chan) -> {
            Bukkit.getScheduler().runTask(getPlugin(), () -> Bukkit.getPluginManager().callEvent(new PlayerProxyJoinEvent(pack.getUuid())));
        });
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9006, ProxyQuitPacket.class, (pack, chan) -> {
            Bukkit.getScheduler().runTask(getPlugin(), () -> Bukkit.getPluginManager().callEvent(new PlayerProxyQuitEvent(pack.getUuid())));
        });
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9007, SyncRequestPacket.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9007, SyncRequestPacket.class, (pack, chan) -> {
            PlayerSyncReceivedEvent ev = new PlayerSyncReceivedEvent(pack.getUuid(), pack.getSyncClass(), pack.getBuf());
            getServer().getPluginManager().callEvent(ev);
            ev.release();
        });
    }
}

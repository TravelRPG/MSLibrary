package kr.msleague.bgmsync.bukkit;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bgmsync.global.packet.SyncRequestPacket;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.function.Consumer;

public class BGMSyncAPI {
    public static void sendRequest(Class<?> type, UUID uuid, Consumer<ByteBuf> buf){
        BCSPBukkitAPI.getInst().sendPacketToProxy(new SyncRequestPacket(type, Bukkit.getPort(), uuid, buf));
    }
}

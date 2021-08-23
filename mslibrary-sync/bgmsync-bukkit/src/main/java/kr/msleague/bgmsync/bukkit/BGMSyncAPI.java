package kr.msleague.bgmsync.bukkit;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bgmsync.global.packet.SyncRequestPacket;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.function.Consumer;

public class BGMSyncAPI {
    /**
     * 이동 대상 서버에 데이터를 실어 패킷 요청을 날립니다.
     *
     * @param type 쏘는 종류의 클래스를 입력합니다.
     * @param uuid Sync할 플레이어 uuid입니다.
     * @param buf  입력될 ByteBuf입니다.
     **/
    public static void sendRequest(Class<?> type, UUID uuid, Consumer<ByteBuf> buf) {
        BCSPBukkitAPI.getInst().sendPacketToProxy(new SyncRequestPacket(type, Bukkit.getPort(), uuid, buf));
    }
}

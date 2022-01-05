package kr.msleague.channel;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import kr.msleague.channel.packet.*;
import org.bukkit.event.Listener;

@LoadPriority(priority = 50)
public class MSChannelInitializer extends MSPlugin implements Listener {
    /**
     * Packet registration process
     */
    @Override
    public void onEnable() {
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9500, PacketIsServerExists.class, PacketIsServerExists::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9501, PacketIsServerExists.Result.class, PacketIsServerExists.Result::new);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9502, PacketIsServerOnline.class, PacketIsServerOnline::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9503, PacketIsServerOnline.Result.class, PacketIsServerOnline.Result::new);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9504, PacketGetServerOnlineCount.class, PacketGetServerOnlineCount::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9505, PacketGetServerOnlineCount.Result.class, PacketGetServerOnlineCount.Result::new);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9506, PacketGetServerUuids.class, PacketGetServerUuids::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9507, PacketGetServerUuids.Result.class, PacketGetServerUuids.Result::new);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9508, PacketGetServerUsernames.class, PacketGetServerUsernames::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9509, PacketGetServerUsernames.Result.class, PacketGetServerUsernames.Result::new);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9510, PacketIsUserOnline.class, PacketIsUserOnline::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9511, PacketIsUserOnline.Result.class, PacketIsUserOnline.Result::new);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9512, PacketGetUserServer.class, PacketGetUserServer::new);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9513, PacketGetUserServer.Result.class, PacketGetUserServer.Result::new);

        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }
}

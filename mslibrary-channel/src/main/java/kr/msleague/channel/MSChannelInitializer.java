package kr.msleague.channel;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.bootstrap.MSPlugin;
import kr.msleague.bootstrap.loadpriority.LoadPriority;
import kr.msleague.channel.packet.*;
import org.bukkit.event.Listener;

@LoadPriority(priority = 50)
public class MSChannelInitializer extends MSPlugin implements Listener {
    @Override
    public void onEnable() {
        BCSPBukkitAPI.getInst().registerOuterPacket(0x9500, PacketIsServerExists.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9501, PacketIsServerExists.Result.class);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9502, PacketIsServerOnline.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9503, PacketIsServerOnline.Result.class);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9504, PacketGetServerOnlineCount.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9505, PacketGetServerOnlineCount.Result.class);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9506, PacketGetServerUuids.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9507, PacketGetServerUuids.Result.class);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9508, PacketGetServerUsernames.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9509, PacketGetServerUsernames.Result.class);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9510, PacketIsUserOnline.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9511, PacketIsUserOnline.Result.class);

        BCSPBukkitAPI.getInst().registerOuterPacket(0x9512, PacketGetUserServer.class);
        BCSPBukkitAPI.getInst().registerInnerPacket(0x9513, PacketGetUserServer.Result.class);

        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }
}

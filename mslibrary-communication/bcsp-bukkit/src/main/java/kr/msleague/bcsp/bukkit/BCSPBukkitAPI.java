package kr.msleague.bcsp.bukkit;

import kr.msleague.bcsp.internal.BCSPApi;
import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.channel.PacketCallBack;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.sys.RelayingPacket;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BCSPBukkitAPI implements BCSPApi {
    @Getter
    private static BCSPBukkitAPI inst;
    private static BCSPBootstrapBukkit main;

    public BCSPBukkitAPI(BCSPBootstrapBukkit xmain) {
        inst = this;
        main = xmain;
    }

    public void sendPacketToProxy(AbstractPacket packet) {
        main.getChannelWrapper().sendPacket(packet);
    }

    public void broadcastPacketToServers(AbstractPacket packet) {
        main.getChannelWrapper().sendPacket(new RelayingPacket(packet));
    }

    public void sendPacketToSpecificServers(int serverPort, AbstractPacket packet) {
        main.getChannelWrapper().sendPacket(new RelayingPacket(packet, String.valueOf(serverPort)));
    }

    public void sendPacketToSpecificServers(String serverName, AbstractPacket packet) {
        main.getChannelWrapper().sendPacket(new RelayingPacket(packet, serverName));
    }

    public <T extends AbstractPacket> void registerOuterPacket(int packetId, Class<T> clazz, Supplier<T> constructor) {
        Direction.OUTBOUND.registerPacket(packetId, clazz, constructor);
    }

    public <T extends AbstractPacket> void registerInnerPacket(int packetId, Class<T> clazz, Supplier<T> constructor) {
        Direction.INBOUND.registerPacket(packetId, clazz, constructor);
    }

    public <T extends AbstractPacket> void registerInnerPacket(int packetId, Class<T> clazz, Supplier<T> constructor, BiConsumer<T, ChannelWrapper> listener) {
        registerInnerPacket(packetId, clazz, constructor);
        addListener(clazz, listener);
    }

    public <T extends AbstractPacket> void addListener(Class<T> clazz, BiConsumer<T, ChannelWrapper> cons) {
        Direction.INBOUND.addListener(clazz, cons);
    }

    public <T extends AbstractPacket> void startCallBack(AbstractPacket toSend, Class<T> type, PacketCallBack<T> onRecieved) {
        main.getChannelWrapper().startCallBack(toSend, type, onRecieved);
    }

    public static class Unsafe {
        public static ChannelWrapper getChannelWrapper() {
            return main.getChannelWrapper();
        }
    }
}

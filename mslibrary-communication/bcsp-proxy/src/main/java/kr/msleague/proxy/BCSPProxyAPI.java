package kr.msleague.proxy;

import kr.msleague.internal.BCSPApi;
import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.packet.AbstractPacket;
import kr.msleague.internal.netty.packet.Direction;
import lombok.Getter;

import java.util.function.BiConsumer;

public class BCSPProxyAPI implements BCSPApi {
    @Getter
    private static BCSPProxyAPI inst;
    private static BCSPBootstrapProxy main;
    public BCSPProxyAPI(BCSPBootstrapProxy xmain){
        inst = this;
        main = xmain;
    }
    @Override
    public void broadcastPacketToServers(AbstractPacket packet) {
        main.channelContainer.portChannelContainer.values().forEach(x->x.sendPacket(packet));
    }

    @Override
    public void sendPacketToSpecificServers(int serverPort, AbstractPacket packet) {
        ChannelWrapper wrapper = main.channelContainer.getChannelByPort(serverPort);
        if(wrapper != null)
            wrapper.sendPacket(packet);
    }

    @Override
    public void sendPacketToSpecificServers(String serverName, AbstractPacket packet) {
        ChannelWrapper wrapper = main.channelContainer.getChannelByServerName(serverName);
        if(wrapper != null)
            wrapper.sendPacket(packet);
    }

    public void registerOuterPacket(int packetId, Class<? extends AbstractPacket> clazz){
        Direction.OUTBOUND.registerPacket(packetId, clazz);
    }
    public<T extends AbstractPacket> void registerInnerPacket(int packetId, Class<T> clazz){
        Direction.INBOUND.registerPacket(packetId, clazz);
    }
    public<T extends AbstractPacket> void registerInnerPacket(int packetId, Class<T> clazz, BiConsumer<T, ChannelWrapper> listener){
        registerInnerPacket(packetId, clazz);
        addListener(clazz, listener);
    }
    public<T extends AbstractPacket> void addListener(Class<T> clazz, BiConsumer<T, ChannelWrapper> cons){
        Direction.INBOUND.addListener(clazz, cons);
    }
    public static class Unsafe{
        public static BCSPProxyChannelContainer getChannelContainer(){
            return main.channelContainer;
        }
    }
}

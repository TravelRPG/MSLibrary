package kr.msleague.bcsp.proxy;

import kr.msleague.bcsp.internal.BCSPApi;
import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;
import kr.msleague.bcsp.internal.netty.channel.PacketCallBack;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import lombok.Getter;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class BCSPProxyAPI implements BCSPApi {
    @Getter
    private static BCSPProxyAPI inst;
    private static BCSPBootstrapProxy main;
    private HashMap<ChannelWrapper, HashMap<Class<? extends AbstractPacket>, ReadWriteLock>> lockMap = new HashMap<>();

    public BCSPProxyAPI(BCSPBootstrapProxy xmain) {
        inst = this;
        main = xmain;
    }

    @Override
    public void broadcastPacketToServers(AbstractPacket packet) {
        main.channelContainer.portChannelContainer.values().forEach(x -> x.sendPacket(packet));
    }

    @Override
    public void sendPacketToSpecificServers(int serverPort, AbstractPacket packet) {
        ChannelWrapper wrapper = main.channelContainer.getChannelByPort(serverPort);
        if (wrapper != null)
            wrapper.sendPacket(packet);
        else
            BCSPLogManager.getLogger().err("Target server not found! (Server: {0})", serverPort);
    }

    @Override
    public void sendPacketToSpecificServers(String serverName, AbstractPacket packet) {
        ChannelWrapper wrapper = main.channelContainer.getChannelByServerName(serverName);
        if (wrapper != null)
            wrapper.sendPacket(packet);
        else
            BCSPLogManager.getLogger().err("Target server not found! (Server: {0})", serverName);
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

    @Override
    public <T extends AbstractPacket> void startCallBack(AbstractPacket toSend, Class<T> type, PacketCallBack<T> onRecieved) {

    }

    private <T extends AbstractPacket> void processCallBackResult(Class<T> pack, ChannelWrapper wrapper) {
        ReadWriteLock lock = lockMap.computeIfAbsent(wrapper, x -> new HashMap<>()).computeIfAbsent(pack, y -> new ReentrantReadWriteLock());
        lock.readLock().lock();
    }

    private void processFinished(Class<? extends AbstractPacket> type, ChannelWrapper wrapper, AbstractPacket packet) {
        ReadWriteLock lock = lockMap.computeIfAbsent(wrapper, x -> new HashMap<>()).computeIfAbsent(type, y -> new ReentrantReadWriteLock());
        try {
            wrapper.sendPacket(packet);
        } finally {
            lock.readLock().unlock();
        }
    }

    public <T extends AbstractPacket> void registerCallBackProcessor(int port, Class<T> targetPacket, Supplier<T> constructor, Function<T, AbstractPacket> func) {
        BCSPProxyAPI.getInst().registerInnerPacket(port, targetPacket, constructor, (pack, wrap) -> {
            AbstractPacket res = null;
            try {
                processCallBackResult(targetPacket, wrap);
                res = func.apply(pack);
                res.setCallBackResult(true);
            } finally {
                processFinished(targetPacket, wrap, res);
            }
        });
    }

    public static class Unsafe {
        public static BCSPProxyChannelContainer getChannelContainer() {
            return main.channelContainer;
        }
    }
}

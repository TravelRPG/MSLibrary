package kr.msleague.bcsp.internal.netty.channel;

import com.google.common.base.Preconditions;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CallBackContainer {
    private static ConcurrentHashMap<Class<? extends AbstractPacket>, Queue<PacketCallBack<? extends AbstractPacket>>> callBackMap = new ConcurrentHashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    protected final void addOnQueue(ChannelWrapper wrapper, AbstractPacket packet, Class<? extends AbstractPacket> targetClass, PacketCallBack<? extends AbstractPacket> callback) {
        Preconditions.checkNotNull(packet, "Packet cannot be null");
        try {
            lock.writeLock().lock();
            wrapper.sendPacket(packet);
            callBackMap.computeIfAbsent(targetClass, k -> new LinkedList<>()).add(callback);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean complete(AbstractPacket packet) {
        Preconditions.checkNotNull(packet, "Packet cannot be null");
        try {
            lock.readLock().lock();
            Queue<PacketCallBack<? extends AbstractPacket>> queue = callBackMap.get(packet.getClass());
            if (queue != null && !queue.isEmpty()) {
                PacketCallBack<AbstractPacket> x = (PacketCallBack<AbstractPacket>) queue.poll();
                if (x == null)
                    return false;
                x.onCallBackRecieved(packet);
                return true;
            }
            return false;
        } finally {
            lock.readLock().unlock();
        }
    }
}

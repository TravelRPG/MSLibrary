package kr.msleague.bcsp.internal.netty.channel;

import com.google.common.base.Preconditions;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CallBackContainer {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private static ConcurrentHashMap<Class<? extends AbstractPacket>, Queue<PacketCallBack<? extends AbstractPacket>>> callBackMap = new ConcurrentHashMap<>();
    protected final void addOnQueue(ChannelWrapper wrapper, AbstractPacket packet, Class<? extends AbstractPacket> targetClass, PacketCallBack<? extends AbstractPacket> callback)
    {
        Preconditions.checkNotNull(packet, "Packet cannot be null");
        lock.writeLock().lock();
        wrapper.sendPacket(packet);
        callBackMap.computeIfAbsent(targetClass, k->new LinkedList<>()).add(callback);
        lock.writeLock().unlock();
    }
    public boolean complete(AbstractPacket packet){
        Preconditions.checkNotNull(packet, "Packet cannot be null");
        lock.readLock().lock();
        Queue<PacketCallBack<? extends AbstractPacket>> queue = callBackMap.get(packet.getClass());
        if(queue != null && !queue.isEmpty()){
            PacketCallBack<AbstractPacket> x = (PacketCallBack<AbstractPacket>) queue.poll();
            if(x == null)
                return false;
            x.onCallBackRecieved(packet);
            lock.readLock().unlock();
            return true;
        }
        lock.readLock().unlock();
        return false;
    }
}

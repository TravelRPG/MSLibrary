package kr.msleague.bcsp.internal.netty.packet;

import kr.msleague.bcsp.internal.netty.channel.ChannelWrapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiConsumer;

public enum Direction{
    /**
     * INBOUND : 상대 -> 내쪽 방향 패킷
     * OUTBOUND : 내쪽 -> 상대 방향 패킷
     */
    INBOUND, OUTBOUND;
    protected HashMap<Integer, Class<? extends AbstractPacket>> intToPackMap;
    protected HashMap<Class<? extends AbstractPacket>, Integer> packToIntMap;
    protected HashMap<Class<? extends AbstractPacket>, LinkedList<BiConsumer<? extends AbstractPacket, ChannelWrapper>>> handlers = new HashMap<>();
    Direction(){
        this.intToPackMap = new HashMap<>();
        this.packToIntMap = new HashMap<>();
    }
    public Direction registerPacket(int packetId, Class<? extends AbstractPacket> pack){
        if(intToPackMap.containsKey(packetId)){
            System.err.println(packetId + "의 패킷 ID가 중복되었습니다. 일부 기능이 작동하지 않을 수 있습니다.");
        }
        intToPackMap.put(packetId, pack);
        packToIntMap.put(pack, packetId);
        return this;
    }
    public final<T extends AbstractPacket> boolean onPacketRecieved(T packet, ChannelWrapper wrapper){
        LinkedList<BiConsumer<? extends AbstractPacket, ChannelWrapper>> list = handlers.get(packet.getClass());
        if(list == null)
            return false;
        for (BiConsumer<? extends AbstractPacket, ChannelWrapper> cons : list) {
            BiConsumer<T, ChannelWrapper> consis = (BiConsumer<T, ChannelWrapper>) cons;
            consis.accept(packet, wrapper);
        }
        return true;
    }
    public<T extends AbstractPacket> void addListener(Class<T> type, BiConsumer<T, ChannelWrapper> cons){
        handlers.computeIfAbsent(type, x->new LinkedList<>()).add(cons);
    }
}
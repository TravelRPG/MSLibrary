package kr.msleague.internal.netty.packet;

public class PacketMapper {
    public static Class<? extends AbstractPacket> getPacketById(Direction direction, int id){
        Class<? extends AbstractPacket> pair = direction.intToPackMap.get(id);
        return pair;
    }
    public static Integer getIdByPacket(Direction direction, Class<? extends AbstractPacket> pack){
        Integer pair = direction.packToIntMap.get(pack);
        return pair;
    }


}

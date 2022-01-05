package kr.msleague.bcsp.internal.netty.packet;

public class PacketMapper {
    public static PacketWrapper<?> getPacketById(Direction direction, int id) {
        return direction.intToPackMap.get(id);
    }

    public static Integer getIdByPacket(Direction direction, Class<? extends AbstractPacket> pack) {
        Integer pair = direction.packToIntMap.get(pack);
        return pair;
    }


}

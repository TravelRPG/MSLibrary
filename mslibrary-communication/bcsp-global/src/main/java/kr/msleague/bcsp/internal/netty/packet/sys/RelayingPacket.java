package kr.msleague.bcsp.internal.netty.packet.sys;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.PacketMapper;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
@Getter
public class RelayingPacket extends AbstractPacket {

    private String targetServer;
    private AbstractPacket packet;

    private byte[] relay;
    public RelayingPacket(AbstractPacket packet, String targetServer){
        this.targetServer = targetServer;
        this.packet = packet;
    }
    public RelayingPacket(AbstractPacket packet){
        this.targetServer = "-1";
        this.packet = packet;
    }
    @Override
    public void read(ByteBuf buf) {
        this.targetServer = ByteBufUtility.readString(buf);
        byte[] relayAble = new byte[buf.readableBytes()];
        buf.readBytes(relayAble);
        this.relay = relayAble;
    }

    @Override
    public void write(ByteBuf buf) {
        Preconditions.checkNotNull(packet);
        ByteBufUtility.writeString(targetServer, buf);
        Integer packetId = PacketMapper.getIdByPacket(Direction.OUTBOUND, packet.getClass());
        ByteBufUtility.writeVarInt(packetId, buf);
        buf.writeBoolean(packet.isCallBackResult());
        packet.write(buf);
    }

    @Override
    public boolean isCallBackResult() {
        return false;
    }

    @Override
    public void setCallBackResult(boolean res) {
        return;
    }
}

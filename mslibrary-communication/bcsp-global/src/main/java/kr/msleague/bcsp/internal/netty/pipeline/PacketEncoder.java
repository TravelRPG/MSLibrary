package kr.msleague.bcsp.internal.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import kr.msleague.bcsp.internal.netty.packet.sys.PingPongPacket;
import kr.msleague.bcsp.internal.netty.packet.sys.RelayingResult;
import kr.msleague.bcsp.internal.netty.exception.PacketEncodeException;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.PacketMapper;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;

import java.util.Arrays;

public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket packet, ByteBuf buf) throws Exception {
        if(packet == null)
            throw new PacketEncodeException(null, "Packet is null");
        if(packet instanceof RelayingResult){
            buf.writeBytes(((RelayingResult)packet).getArray());
            return;
        }
        Integer packetId = PacketMapper.getIdByPacket(Direction.OUTBOUND, packet.getClass());
        if(packetId == null)
            throw new PacketEncodeException(packet, "Packet is not registered to outbound");
        ByteBufUtility.writeVarInt(packetId, buf);
        buf.writeBoolean(packet.isCallBackResult());
        packet.write(buf);
    }
}

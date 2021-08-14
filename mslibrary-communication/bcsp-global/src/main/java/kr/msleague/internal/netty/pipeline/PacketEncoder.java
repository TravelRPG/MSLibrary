package kr.msleague.internal.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import kr.msleague.internal.netty.exception.PacketEncodeException;
import kr.msleague.internal.netty.packet.AbstractPacket;
import kr.msleague.internal.netty.packet.Direction;
import kr.msleague.internal.netty.packet.PacketMapper;
import kr.msleague.internal.netty.packet.sys.RelayingResult;
import kr.msleague.internal.netty.util.ByteBufUtility;

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

        buf.markReaderIndex();
        byte[] barr = new byte[buf.readableBytes()];
        buf.readBytes(barr);
        System.out.println(Arrays.toString(barr));
        buf.resetReaderIndex();
    }
}

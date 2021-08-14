package kr.msleague.bcsp.internal.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import kr.msleague.bcsp.internal.netty.exception.PacketException;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.PacketMapper;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;

import java.util.Arrays;
import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        buf.markReaderIndex();
        byte[] barr = new byte[buf.readableBytes()];
        buf.readBytes(barr);
        System.out.println(Arrays.toString(barr));

        buf.resetReaderIndex();
        int packetId = ByteBufUtility.readVarInt(buf);
        boolean callBackResult = buf.readBoolean();
        Class<? extends AbstractPacket> clazz = PacketMapper.getPacketById(Direction.INBOUND, packetId);
        if(clazz == null)
            throw new PacketException(null, "Unregistered packet id " + packetId);
        AbstractPacket packet;
        try{
            packet = clazz.newInstance();
        }catch(Exception ex){
            throw new PacketException(null, "Packet must contains NoArgsConstructor : "+clazz.getSimpleName());
        }
        packet.setCallBackResult(callBackResult);
        packet.read(buf);
        list.add(packet);
    }
}

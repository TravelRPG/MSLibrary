package kr.msleague.bcsp.internal.netty.packet.sys;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class RelayingResult extends AbstractPacket {
    private byte[] array;
    @Override
    public void read(ByteBuf buf) {
        throw new UnsupportedOperationException("Relaying Result shouldn't access read method.");
    }

    @Override
    public void write(ByteBuf buf) {
        throw new UnsupportedOperationException("Relaying Result shouldn't access write method.");
    }
}

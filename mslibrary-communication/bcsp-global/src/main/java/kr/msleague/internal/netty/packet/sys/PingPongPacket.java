package kr.msleague.internal.netty.packet.sys;

import io.netty.buffer.ByteBuf;
import kr.msleague.internal.netty.packet.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PingPongPacket extends AbstractPacket {
    private long recievedTime;
    private long time;
    @Override
    public void read(ByteBuf buf) {
        this.recievedTime = buf.readLong();
        this.time = buf.readLong();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(recievedTime);
        buf.writeLong(time);
    }
}

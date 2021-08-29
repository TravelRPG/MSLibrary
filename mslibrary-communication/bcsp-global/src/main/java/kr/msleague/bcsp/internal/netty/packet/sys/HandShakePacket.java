package kr.msleague.bcsp.internal.netty.packet.sys;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HandShakePacket extends AbstractPacket {
    private long authCodeA, authCodeB;
    private int serverPort;

    public HandShakePacket(long authCodeA, long authCodeB, int serverPort) {
        this.authCodeA = authCodeA;
        this.authCodeB = authCodeB;
        this.serverPort = serverPort;
    }

    @Override
    public void read(ByteBuf buf) {
        this.authCodeA = buf.readLong();
        this.authCodeB = buf.readLong();
        this.serverPort = buf.readInt();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeLong(this.authCodeA);
        buf.writeLong(this.authCodeB);
        buf.writeInt(this.serverPort);
    }
}

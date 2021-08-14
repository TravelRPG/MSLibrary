package kr.msleague.bgmsync.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.function.Consumer;

@Getter
@NoArgsConstructor
public class SyncRequestPacket extends AbstractPacket {
    private int fromPort;
    private UUID uuid;
    private Consumer<ByteBuf> cons;
    private byte[] buf;
    public SyncRequestPacket(int a, UUID b, Consumer<ByteBuf> c){
        this.fromPort = a;
        this.uuid = b;
        this.cons = c;
    }
    @Override
    public void read(ByteBuf buf) {
        this.uuid = ByteBufUtility.readUUID(buf);
        this.fromPort = buf.readInt();
        byte[] arr = new byte[buf.readableBytes() - ]
        this.buf =
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtility.writeUUID(uuid, buf);
        buf.writeInt(fromPort);
        cons.accept(buf);
    }
}

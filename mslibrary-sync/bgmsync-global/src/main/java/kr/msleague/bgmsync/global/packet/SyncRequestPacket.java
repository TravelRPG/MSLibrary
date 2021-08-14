package kr.msleague.bgmsync.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
@NoArgsConstructor
public class SyncRequestPacket extends AbstractPacket {
    private int fromPort;
    private UUID uuid;
    private String syncClass;
    private Consumer<ByteBuf> cons;
    private byte[] buf;
    public SyncRequestPacket(Class<?> type,int a, UUID b, Consumer<ByteBuf> c){
        this.syncClass = type.getSimpleName();
        this.fromPort = a;
        this.uuid = b;
        this.cons = c;
    }

    public SyncRequestPacket(String syncClass, int fromPort, UUID uuid, Consumer<ByteBuf> c) {
        this.syncClass = syncClass;
        this.fromPort = fromPort;
        this.uuid = uuid;
        this.cons = c;
    }

    @Override
    public void read(ByteBuf buf) {
        this.syncClass = ByteBufUtility.readString(buf);
        this.uuid = ByteBufUtility.readUUID(buf);
        this.fromPort = buf.readInt();
        this.buf = new byte[buf.readableBytes()];
        buf.readBytes(this.buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtility.writeString(syncClass, buf);
        ByteBufUtility.writeUUID(uuid, buf);
        buf.writeInt(fromPort);
        cons.accept(buf);
    }
}

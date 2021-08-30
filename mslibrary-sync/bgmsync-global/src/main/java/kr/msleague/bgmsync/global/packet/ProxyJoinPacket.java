package kr.msleague.bgmsync.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProxyJoinPacket extends AbstractPacket {
    private UUID uuid;

    @Override
    public void read(ByteBuf buf) {
        this.uuid = ByteBufUtility.readUUID(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtility.writeUUID(this.uuid, buf);
    }
}

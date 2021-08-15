package kr.msleague.teleport.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class PlayerTeleportPacket extends AbstractPacket {
    private UUID player;
    private UUID location;
    public PlayerTeleportPacket(UUID player, UUID location){
        this.player = player;
        this.location = location;
    }
    public void write(ByteBuf buf){
        buf.writeLong(player.getMostSignificantBits());
        buf.writeLong(player.getLeastSignificantBits());
        buf.writeLong(location.getMostSignificantBits());
        buf.writeLong(location.getLeastSignificantBits());
    }
    public void read(ByteBuf buf){
        UUID who = new UUID(buf.readLong(), buf.readLong());
        UUID to = new UUID(buf.readLong(), buf.readLong());
        this.player = who;
        this.location = to;
    }
}

package kr.msleague.teleport.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.teleport.global.GLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class LocationTeleportPacket extends AbstractPacket {
    private UUID player;
    private GLocation location;

    public LocationTeleportPacket(UUID player, GLocation location) {
        this.player = player;
        this.location = location;
    }

    public void write(ByteBuf buf) {
        location.writer(buf);
        buf.writeLong(player.getMostSignificantBits());
        buf.writeLong(player.getLeastSignificantBits());
    }

    public void read(ByteBuf buf) {
        GLocation gloc = GLocation.read(buf);
        UUID who = new UUID(buf.readLong(), buf.readLong());
        this.player = who;
        this.location = gloc;
    }

}

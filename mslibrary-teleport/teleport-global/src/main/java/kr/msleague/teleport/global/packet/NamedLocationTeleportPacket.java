package kr.msleague.teleport.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import kr.msleague.teleport.global.GLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NamedLocationTeleportPacket extends AbstractPacket {
    private String player;
    private GLocation location;
    public NamedLocationTeleportPacket(String player, GLocation location){
        this.player = player;
        this.location = location;
    }
    public void write(ByteBuf buf){
        location.writer(buf);
        ByteBufUtility.writeString(player, buf);
    }
    public void read(ByteBuf buf){
        GLocation gloc = GLocation.read(buf);
        String who = ByteBufUtility.readString(buf);
        this.player = who;
        this.location = gloc;
    }

}

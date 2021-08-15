package kr.msleague.teleport.global.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlayerNameTeleportPacket extends AbstractPacket {
    private String player;
    private String location;
    public PlayerNameTeleportPacket(String player, String location){
        this.player = player;
        this.location = location;
    }
    public void write(ByteBuf buf){
        ByteBufUtility.writeString(player, buf);
        ByteBufUtility.writeString(location, buf);
    }
    public void read(ByteBuf buf){
        String player = ByteBufUtility.readString(buf);
        String location = ByteBufUtility.readString(buf);
        this.player = player;
        this.location = location;
    }
}

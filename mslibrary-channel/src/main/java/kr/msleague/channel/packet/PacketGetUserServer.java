package kr.msleague.channel.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class PacketGetUserServer extends AbstractPacket {
    private String username;
    public PacketGetUserServer(UUID uuid){
        username = uuid.toString();
    }
    public PacketGetUserServer(String username){
        this.username = username;
    }
    @Override
    public void read(ByteBuf buf) {
        this.username = ByteBufUtility.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtility.writeString(username, buf);
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Result extends AbstractPacket{
        private String server;
        @Override
        public void read(ByteBuf buf) {
            this.server = ByteBufUtility.readString(buf);
        }

        @Override
        public void write(ByteBuf buf) {
            ByteBufUtility.writeString(server, buf);
        }
    }
}

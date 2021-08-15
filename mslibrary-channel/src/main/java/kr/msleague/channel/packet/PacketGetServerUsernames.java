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
public class PacketGetServerUsernames extends AbstractPacket {
    private String server;
    public PacketGetServerUsernames(int port){
        server = String.valueOf(port);
    }
    public PacketGetServerUsernames(String serverName){
        this.server = serverName;
    }
    @Override
    public void read(ByteBuf buf) {
        this.server = ByteBufUtility.readString(buf);
    }

    @Override
    public void write(ByteBuf buf) {
        ByteBufUtility.writeString(server, buf);
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Result extends AbstractPacket{
        private List<String> result;
        @Override
        public void read(ByteBuf buf) {
            int size = buf.readInt();
            result = new ArrayList<>(size);
            for(int i = 0; i < size; i++){
                String read = ByteBufUtility.readString(buf);
                result.add(read);
            }
        }

        @Override
        public void write(ByteBuf buf) {
            buf.writeInt(result.size());
            for(int i = 0; i < result.size(); i++){
                ByteBufUtility.writeString(result.get(i), buf);
            }
        }
    }
}

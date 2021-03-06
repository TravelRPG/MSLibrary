package kr.msleague.channel.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PacketIsServerOnline extends AbstractPacket {
    private String server;

    public PacketIsServerOnline(int port) {
        server = String.valueOf(port);
    }

    public PacketIsServerOnline(String serverName) {
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
    public static class Result extends AbstractPacket {
        private boolean result;

        @Override
        public void read(ByteBuf buf) {
            this.result = buf.readBoolean();
        }

        @Override
        public void write(ByteBuf buf) {
            buf.writeBoolean(this.result);
        }
    }
}

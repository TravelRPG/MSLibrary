package kr.msleague.channel.packet;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.util.ByteBufUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class PacketIsUserOnline extends AbstractPacket {
    private String username;

    public PacketIsUserOnline(UUID uuid) {
        username = uuid.toString();
    }

    public PacketIsUserOnline(String username) {
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

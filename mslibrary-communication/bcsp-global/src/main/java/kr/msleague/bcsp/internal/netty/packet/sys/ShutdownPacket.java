package kr.msleague.bcsp.internal.netty.packet.sys;

import io.netty.buffer.ByteBuf;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public final class ShutdownPacket extends AbstractPacket {
    private boolean shutdownTarget;
    @Override
    public void read(ByteBuf buf) {
        this.shutdownTarget = buf.readBoolean();
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeBoolean(this.shutdownTarget);
    }

    @Override
    public final boolean isCallBackResult() {
        return false;
    }

    @Override
    public final void setCallBackResult(boolean res) {
        return;
    }
}

package kr.msleague.internal.netty.packet;

import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket{
    private boolean callBackResult = false;
    public void setCallBackResult(boolean res){
        this.callBackResult = res;
    }
    public boolean isCallBackResult(){
        return this.callBackResult;
    }
    public abstract void read(ByteBuf buf);
    public abstract void write(ByteBuf buf);
}

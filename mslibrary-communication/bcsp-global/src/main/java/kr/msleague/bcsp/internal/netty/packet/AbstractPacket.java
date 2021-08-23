package kr.msleague.bcsp.internal.netty.packet;

import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket{
    /**
     * 내부 콜백을 사용하려고 할 경우 이 값을 "true"로 변경하세요
     * 단, 콜백은 노드길이 1 (EX, Proxy->Bukkit1 , Bukkit2->Proxy 등)
     * 만 지원함으로, Relay 기능을 이용해서 Bukkit1->Bukkit2 등의 노드길이 2 이상의 콜백을 사용할 경우
     * 기본지원 콜백 시스템으로 이를 지원하지 않음으로 이 값을 변경할 필요가 없습니다.
     * 이 값을 "true"로 변경시 기본 패킷 처리 메커니즘을 따라가지 않으므로 적절한 콜백 리스너를
     * 수신측에서 등록하지 않을 경우 패킷이 무시될 수 있습니다.
     */
    private boolean callBackResult = false;
    public void setCallBackResult(boolean res){
        this.callBackResult = res;
    }
    public boolean isCallBackResult(){
        return this.callBackResult;
    }

    /**
     * 패킷 역직렬화를 위한 부분입니다
     * @param buf 읽을 대상 바이트버퍼 입니다
     */
    public abstract void read(ByteBuf buf);

    /**
     * 패킷 직렬화를 위한 부분입니다
     * @param buf 작성할 대상 바이트버퍼 부분입니다.
     */
    public abstract void write(ByteBuf buf);
}

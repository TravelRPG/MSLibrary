package kr.msleague.internal;

import kr.msleague.internal.netty.channel.ChannelWrapper;
import kr.msleague.internal.netty.packet.AbstractPacket;
import java.util.function.BiConsumer;

public interface BCSPApi {
    /**
     *  모든 서버에 패킷을 날립니다.
     *
     * @param packet
     *        날릴 대상 패킷입니다.
     *
     **/
    void broadcastPacketToServers(AbstractPacket packet);
    /**
     *  특정 서버(포트 기반)에 패킷을 날립니다.
     *
     * @param serverPort
     *        대상 서버 포트를 입력하세요
     *
     * @param packet
     *        대상 패킷을 입력하세요
     *
     **/
    void sendPacketToSpecificServers(int serverPort, AbstractPacket packet);
    /**
     *  특정 서버(서버이름 기반)에 패킷을 날립니다.
     *
     * @param serverName
     *        대상 서버 이름을 입력하세요
     *
     * @param packet
     *        대상 패킷을 입력하세요
     *
     **/
    void sendPacketToSpecificServers(String serverName, AbstractPacket packet);
    /**
     *  커스텀 패킷(내부->외부 방향)을 등록합니다.
     *
     * @param packetId
     *        패킷 구분에 사용할 Integer의 id값입니다.
     *        9000번대는 내부적으로 사용중입니다. 피해서 사용해주세요.
     *
     * @param clazz
     *        대상 패킷 클래스를 입력하세요.
     *
     **/
    void registerOuterPacket(int packetId, Class<? extends AbstractPacket> clazz);
    /**
     *  커스텀 패킷(외부->내부 방향)을 등록합니다.
     *
     * @param packetId
     *        패킷 구분에 사용할 Integer의 id값입니다.
     *        9000번대는 내부적으로 사용중입니다. 피해서 사용해주세요.
     *
     * @param clazz
     *        대상 패킷 클래스를 입력하세요.
     *
     **/
    <T extends AbstractPacket> void registerInnerPacket(int packetId, Class<T> clazz);
    /**
     *  커스텀 패킷(외부->내부 방향)을 등록하고, 패킷이 수신되었을때 발동하는 Listener을 동시에 등록합니다.
     *
     * @param packetId
     *        패킷 구분에 사용할 Integer의 id값입니다.
     *        9000번대는 내부적으로 사용중입니다. 피해서 사용해주세요.
     *
     * @param clazz
     *        대상 패킷 클래스를 입력하세요.
     *
     **/
    <T extends AbstractPacket> void registerInnerPacket(int packetId, Class<T> clazz, BiConsumer<T, ChannelWrapper> listener);
    /**
     *  패킷이 수신되었을때 발동하는 Listener을 추가합니다.
     *
     *
     * @param clazz
     *        대상 패킷 클래스를 입력하세요.
     *
     **/
    <T extends AbstractPacket> void addListener(Class<T> clazz, BiConsumer<T, ChannelWrapper> cons);
}

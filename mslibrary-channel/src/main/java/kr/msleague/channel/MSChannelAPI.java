package kr.msleague.channel;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface MSChannelAPI {
    /**
     * 서버가 존재하는지 확인한 후 결과를 퓨처로 콜백합니다
     * @param server 대상 서버 이름입니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerExists(String server);

    /**
     * 서버가 존재하는지 확인한 후 결과를 퓨처로 콜백합니다
     * @param port 대상 서버 포트입니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerExists(int port);

    /**
     * 서버가 열려있는지 확인합니다
     * @param server 대상 서버 이름입니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerOnline(String server);

    /**
     * 서버가 열려있는지 확인합니다
     * @param port 대상 서버 포트입니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerOnline(int port);

    /**
     * 서버의 온라인 유저 수를 확인합니다
     * @param server 대상 서버 이름입니다.
     * @return 콜백 결과입니다.
     */
    Future<Integer> getServerOnlineCount(String server);

    /**
     * 서버의 온라인 유저 수를 확인합니다
     * @param port 대상 서버 포트입니다.
     * @return 콜백 결과입니다.
     */
    Future<Integer> getServerOnlineCount(int port);

    /**
     * Proxy 서버의 온라인 유저 수를 확인합니다
     * @return 콜백 결과입니다.
     */
    Future<Integer> getProxyOnlinePlayerCount();

    /**
     * Proxy 서버의 모든 온라인 유저를 가져옵니다
     * @return 콜백 결과입니다.
     */
    Future<List<UUID>> getAllUuidOfProxy();

    Future<List<String>> getAllUsernameOfProxy();

    Future<List<UUID>> getAllUuidOfServer(String server);

    Future<List<UUID>> getAllUuidOfServer(int port);

    Future<List<String>> getAllUsernameOfServer(String server);

    Future<List<String>> getAllUsernameOfServer(int port);

    Future<Boolean> isUserOnline(UUID player);

    Future<Boolean> isUserOnline(String playerName);

    Future<String> getServerOfUser(UUID player);

    Future<String> getServerOfUser(String playerName);
}

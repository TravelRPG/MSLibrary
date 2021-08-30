package kr.msleague.channel;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface MSChannelAPI {
    /**
     * 서버가 존재하는지 확인한 후 결과를 퓨처로 콜백합니다
     * @param server 대상 서버 이름입니다.
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerExists(String server, Consumer<Boolean>... cons);

    /**
     * 서버가 존재하는지 확인한 후 결과를 퓨처로 콜백합니다
     * @param port 대상 서버 포트입니다.
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerExists(int port, Consumer<Boolean>... cons);

    /**
     * 서버가 열려있는지 확인합니다
     * @param server 대상 서버 이름입니다.
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerOnline(String server, Consumer<Boolean>... cons);

    /**
     * 서버가 열려있는지 확인합니다
     * @param port 대상 서버 포트입니다.
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다. Boolean(T/F)를 빈환합니다
     */
    Future<Boolean> isServerOnline(int port, Consumer<Boolean>... cons);

    /**
     * 서버의 온라인 유저 수를 확인합니다
     * @param server 대상 서버 이름입니다.
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다.
     */
    Future<Integer> getServerOnlineCount(String server, Consumer<Integer>... cons);

    /**
     * 서버의 온라인 유저 수를 확인합니다
     * @param port 대상 서버 포트입니다.
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다.
     */
    Future<Integer> getServerOnlineCount(int port, Consumer<Integer>... cons);

    /**
     * Proxy 서버의 온라인 유저 수를 확인합니다
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다.
     */
    Future<Integer> getProxyOnlinePlayerCount(Consumer<Integer>... cons);

    /**
     * Proxy 서버의 모든 온라인 유저를 가져옵니다
     * @param cons 퓨처 대신 컨슈머 형태로 인라인 코딩을 할 수 있습니다.
     * @return 콜백 결과입니다.
     */
    Future<List<UUID>> getAllUuidOfProxy(Consumer<List<UUID>>... cons);

    Future<List<String>> getAllUsernameOfProxy(Consumer<List<String>>... cons);

    Future<List<UUID>> getAllUuidOfServer(String server, Consumer<List<UUID>>... cons);

    Future<List<UUID>> getAllUuidOfServer(int port, Consumer<List<UUID>>... cons);

    Future<List<String>> getAllUsernameOfServer(String server, Consumer<List<String>>... cons);

    Future<List<String>> getAllUsernameOfServer(int port, Consumer<List<String>>... cons);

    Future<Boolean> isUserOnline(UUID player, Consumer<Boolean>... cons);

    Future<Boolean> isUserOnline(String playerName, Consumer<Boolean>... cons);

    Future<String> getServerOfUser(UUID player, Consumer<String>... cons);

    Future<String> getServerOfUser(String playerName, Consumer<String>... cons);
}

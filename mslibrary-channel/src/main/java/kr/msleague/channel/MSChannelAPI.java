package kr.msleague.channel;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface MSChannelAPI {
    void isServerExists(String server, Consumer<Boolean> cons);
    void isServerExists(int port, Consumer<Boolean> cons);
    void isServerOnline(String server, Consumer<Boolean> cons);
    void isServerOnline(int port, Consumer<Boolean> cons);

    void getServerOnlineCount(String server, Consumer<Integer> cons);
    void getServerOnlineCount(int port, Consumer<Integer> cons);
    void getProxyOnlinePlayerCount(Consumer<Integer> cons);

    void getAllUuidOfProxy(Consumer<List<UUID>> cons);
    void getAllUsernameOfProxy(Consumer<List<String>> cons);

    void getAllUuidOfServer(String server, Consumer<List<UUID>> cons);
    void getAllUuidOfServer(int port, Consumer<List<UUID>> cons);
    void getAllUsernameOfServer(String server, Consumer<List<String>> cons);
    void getAllUsernameOfServer(int port, Consumer<List<String>> cons);

    void isUserOnline(UUID player, Consumer<Boolean> cons);
    void isUserOnline(String playerName, Consumer<Boolean> cons);
    void getServerOfUser(UUID player, Consumer<String> cons);
    void getServerOfUser(String playerName, Consumer<String> cons);
}

package kr.msleague.channel;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface MSChannelAPI {
    Future<Boolean> isServerExists(String server, Consumer<Boolean>... cons);

    Future<Boolean> isServerExists(int port, Consumer<Boolean>... cons);

    Future<Boolean> isServerOnline(String server, Consumer<Boolean>... cons);

    Future<Boolean> isServerOnline(int port, Consumer<Boolean>... cons);

    Future<Integer> getServerOnlineCount(String server, Consumer<Integer>... cons);

    Future<Integer> getServerOnlineCount(int port, Consumer<Integer>... cons);

    Future<Integer> getProxyOnlinePlayerCount(Consumer<Integer>... cons);

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

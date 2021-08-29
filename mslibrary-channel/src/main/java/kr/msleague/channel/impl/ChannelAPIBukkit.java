package kr.msleague.channel.impl;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.channel.MSChannelAPI;
import kr.msleague.channel.packet.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ChannelAPIBukkit implements MSChannelAPI {
    private static ChannelAPIBukkit inst;

    public static ChannelAPIBukkit getInstance() {
        if (inst == null)
            inst = new ChannelAPIBukkit();
        return inst;
    }

    @SafeVarargs
    @Override
    public final Future<Boolean> isServerExists(String server, Consumer<Boolean>... cons) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerExists(server), PacketIsServerExists.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.isResult());
            }
            future.complete(res.isResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Boolean> isServerExists(int port, Consumer<Boolean>... cons) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerExists(port), PacketIsServerExists.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.isResult());
            }
            future.complete(res.isResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Boolean> isServerOnline(String server, Consumer<Boolean>... cons) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerOnline(server), PacketIsServerOnline.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.isResult());
            }
            future.complete(res.isResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Boolean> isServerOnline(int port, Consumer<Boolean>... cons) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerOnline(port), PacketIsServerOnline.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.isResult());
            }
            future.complete(res.isResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Integer> getServerOnlineCount(String server, Consumer<Integer>... cons) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(server), PacketGetServerOnlineCount.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Integer> getServerOnlineCount(int port, Consumer<Integer>... cons) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(port), PacketGetServerOnlineCount.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Integer> getProxyOnlinePlayerCount(Consumer<Integer>... cons) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(-1), PacketGetServerOnlineCount.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<List<UUID>> getAllUuidOfProxy(Consumer<List<UUID>>... cons) {
        CompletableFuture<List<UUID>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(-1), PacketGetServerUuids.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<List<String>> getAllUsernameOfProxy(Consumer<List<String>>... cons) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(-1), PacketGetServerUsernames.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<List<UUID>> getAllUuidOfServer(String server, Consumer<List<UUID>>... cons) {
        CompletableFuture<List<UUID>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(server), PacketGetServerUuids.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<List<UUID>> getAllUuidOfServer(int port, Consumer<List<UUID>>... cons) {
        CompletableFuture<List<UUID>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(port), PacketGetServerUuids.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<List<String>> getAllUsernameOfServer(String server, Consumer<List<String>>... cons) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(server), PacketGetServerUsernames.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<List<String>> getAllUsernameOfServer(int port, Consumer<List<String>>... cons) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(port), PacketGetServerUsernames.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getResult());
            }
            future.complete(res.getResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Boolean> isUserOnline(UUID player, Consumer<Boolean>... cons) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsUserOnline(player), PacketIsUserOnline.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.isResult());
            }
            future.complete(res.isResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<Boolean> isUserOnline(String playerName, Consumer<Boolean>... cons) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsUserOnline(playerName), PacketIsUserOnline.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.isResult());
            }
            future.complete(res.isResult());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<String> getServerOfUser(UUID player, Consumer<String>... cons) {
        CompletableFuture<String> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetUserServer(player), PacketGetUserServer.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getServer());
            }
            future.complete(res.getServer());
        });
        return future;
    }

    @SafeVarargs
    @Override
    public final Future<String> getServerOfUser(String playerName, Consumer<String>... cons) {
        CompletableFuture<String> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetUserServer(playerName), PacketGetUserServer.Result.class, res -> {
            if (cons.length > 0) {
                cons[0].accept(res.getServer());
            }
            future.complete(res.getServer());
        });
        return future;
    }

}

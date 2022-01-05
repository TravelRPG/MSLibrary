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

    @Override
    public final CompletableFuture<Boolean> isServerExists(String server) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerExists(server), PacketIsServerExists.Result.class, res -> {
            future.complete(res.isResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Boolean> isServerExists(int port) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerExists(port), PacketIsServerExists.Result.class, res -> {
            future.complete(res.isResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Boolean> isServerOnline(String server) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerOnline(server), PacketIsServerOnline.Result.class, res -> {
            future.complete(res.isResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Boolean> isServerOnline(int port) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerOnline(port), PacketIsServerOnline.Result.class, res -> {
            future.complete(res.isResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Integer> getServerOnlineCount(String server) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(server), PacketGetServerOnlineCount.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Integer> getServerOnlineCount(int port) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(port), PacketGetServerOnlineCount.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Integer> getProxyOnlinePlayerCount() {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(-1), PacketGetServerOnlineCount.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<List<UUID>> getAllUuidOfProxy() {
        CompletableFuture<List<UUID>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(-1), PacketGetServerUuids.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<List<String>> getAllUsernameOfProxy() {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(-1), PacketGetServerUsernames.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<List<UUID>> getAllUuidOfServer(String server) {
        CompletableFuture<List<UUID>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(server), PacketGetServerUuids.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<List<UUID>> getAllUuidOfServer(int port) {
        CompletableFuture<List<UUID>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(port), PacketGetServerUuids.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<List<String>> getAllUsernameOfServer(String server) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(server), PacketGetServerUsernames.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<List<String>> getAllUsernameOfServer(int port) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(port), PacketGetServerUsernames.Result.class, res -> {
            future.complete(res.getResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Boolean> isUserOnline(UUID player) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsUserOnline(player), PacketIsUserOnline.Result.class, res -> {
            future.complete(res.isResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<Boolean> isUserOnline(String playerName) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsUserOnline(playerName), PacketIsUserOnline.Result.class, res -> {
            future.complete(res.isResult());
        });
        return future;
    }

    @Override
    public final CompletableFuture<String> getServerOfUser(UUID player) {
        CompletableFuture<String> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetUserServer(player), PacketGetUserServer.Result.class, res -> {
            future.complete(res.getServer());
        });
        return future;
    }

    @Override
    public final CompletableFuture<String> getServerOfUser(String playerName) {
        CompletableFuture<String> future = new CompletableFuture<>();
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetUserServer(playerName), PacketGetUserServer.Result.class, res -> {
            future.complete(res.getServer());
        });
        return future;
    }

}

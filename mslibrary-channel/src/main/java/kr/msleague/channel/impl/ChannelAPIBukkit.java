package kr.msleague.channel.impl;

import kr.msleague.bcsp.bukkit.BCSPBukkitAPI;
import kr.msleague.channel.MSChannelAPI;
import kr.msleague.channel.packet.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ChannelAPIBukkit implements MSChannelAPI {
    @Override
    public void isServerExists(String server, Consumer<Boolean> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerExists(server), PacketIsServerExists.Result.class, res->cons.accept(res.isResult()));
    }

    @Override
    public void isServerExists(int port, Consumer<Boolean> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerExists(port), PacketIsServerExists.Result.class, res->cons.accept(res.isResult()));
    }

    @Override
    public void isServerOnline(String server, Consumer<Boolean> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerOnline(server), PacketIsServerOnline.Result.class, res->cons.accept(res.isResult()));
    }

    @Override
    public void isServerOnline(int port, Consumer<Boolean> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsServerOnline(port), PacketIsServerOnline.Result.class, res->cons.accept(res.isResult()));
    }

    @Override
    public void getServerOnlineCount(String server, Consumer<Integer> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(server), PacketGetServerOnlineCount.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getServerOnlineCount(int port, Consumer<Integer> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(port), PacketGetServerOnlineCount.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getProxyOnlinePlayerCount(Consumer<Integer> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerOnlineCount(-1), PacketGetServerOnlineCount.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getAllUuidOfProxy(Consumer<List<UUID>> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(-1), PacketGetServerUuids.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getAllUsernameOfProxy(Consumer<List<String>> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(-1), PacketGetServerUsernames.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getAllUuidOfServer(String server, Consumer<List<UUID>> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(server), PacketGetServerUuids.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getAllUuidOfServer(int port, Consumer<List<UUID>> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUuids(port), PacketGetServerUuids.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getAllUsernameOfServer(String server, Consumer<List<String>> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(server), PacketGetServerUsernames.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void getAllUsernameOfServer(int port, Consumer<List<String>> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetServerUsernames(port), PacketGetServerUsernames.Result.class, res->cons.accept(res.getResult()));
    }

    @Override
    public void isUserOnline(UUID player, Consumer<Boolean> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsUserOnline(player), PacketIsUserOnline.Result.class, res->cons.accept(res.isResult()));
    }

    @Override
    public void isUserOnline(String playerName, Consumer<Boolean> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketIsUserOnline(playerName), PacketIsUserOnline.Result.class, res->cons.accept(res.isResult()));
    }

    @Override
    public void getServerOfUser(UUID player, Consumer<String> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetUserServer(player), PacketGetUserServer.Result.class, res->cons.accept(res.getServer()));
    }

    @Override
    public void getServerOfUser(String playerName, Consumer<String> cons) {
        BCSPBukkitAPI.getInst().startCallBack(new PacketGetUserServer(playerName), PacketGetUserServer.Result.class, res->cons.accept(res.getServer()));
    }
    private static ChannelAPIBukkit inst;
    public static ChannelAPIBukkit getInstance(){
        if(inst == null)
            inst = new ChannelAPIBukkit();
        return inst;
    }

}

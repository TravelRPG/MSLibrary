package kr.msleague.bcsp.bukkit;

import io.netty.channel.Channel;
import kr.msleague.bootstrap.MSLibraryBukkitBootstrap;
import kr.msleague.bcsp.bukkit.event.connection.BCSPDisconnectedEvent;
import kr.msleague.bcsp.bukkit.event.packet.ASyncPacketReceivedEvent;
import kr.msleague.bcsp.bukkit.event.packet.PacketReceivedEvent;
import kr.msleague.bcsp.internal.BungeeComsoServerBootStrap;
import kr.msleague.bcsp.internal.logger.BCSPLogManager;
import kr.msleague.bcsp.internal.netty.packet.sys.HandShakePacket;
import kr.msleague.bcsp.internal.netty.pipeline.BossHandler;
import kr.msleague.bcsp.internal.netty.pipeline.ConnectionState;
import kr.msleague.bcsp.internal.netty.pipeline.TimeOutHandler;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ChannelConnecterThread implements Runnable {
    @Override
    public void run(){
        CompletableFuture<Channel> future = BungeeComsoServerBootStrap.initClient();
        future.whenCompleteAsync((channel,throwable)->{
            if(throwable != null){
                BCSPLogManager.getLogger().err("BCSP Failed to bootup successfully. Check following stack trace. Trying connect after 3 seconds");
                throwable.printStackTrace();
                try {
                    TimeUnit.SECONDS.sleep(3);
                    run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
            BossHandler handlerBoss = channel.pipeline().get(BossHandler.class);
            handlerBoss.setDisconnectHandler((x)->{
                if(!BCSPBootstrapBukkit.isActive())
                    return;
                Bukkit.getScheduler().runTask(MSLibraryBukkitBootstrap.getPlugin(), ()->Bukkit.getPluginManager().callEvent(new BCSPDisconnectedEvent(x, x.getHandler().getConnectionState())));
                run();
            });
            handlerBoss.setPacketPreProcessHandler((handler,wrapper)->{
                Bukkit.getPluginManager().callEvent(new ASyncPacketReceivedEvent(handler, wrapper));
                Bukkit.getScheduler().runTask(MSLibraryBukkitBootstrap.getPlugin(), ()->Bukkit.getPluginManager().callEvent(new PacketReceivedEvent(handler, wrapper)));
            });
            BCSPLogManager.getLogger().info("BSCP Server Initialization Success!");
            handlerBoss.setConnectionState(ConnectionState.HANDSHAKING);
            channel.pipeline().addFirst("timeout-handler", new TimeOutHandler(5L, TimeUnit.SECONDS));
            channel.writeAndFlush(new HandShakePacket(BCSPBootstrapBukkit.getAuthCodeA(), BCSPBootstrapBukkit.getAuthCodeB(), Bukkit.getPort()));
        });
    }
}

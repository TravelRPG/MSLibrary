package kr.msleague.bcsp.internal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import kr.msleague.bcsp.internal.netty.packet.AbstractPacket;
import kr.msleague.bcsp.internal.netty.packet.Direction;
import kr.msleague.bcsp.internal.netty.packet.sys.HandShakePacket;
import kr.msleague.bcsp.internal.netty.packet.sys.PingPongPacket;
import kr.msleague.bcsp.internal.netty.packet.sys.RelayingPacket;
import kr.msleague.bcsp.internal.netty.packet.sys.ShutdownPacket;
import kr.msleague.bcsp.internal.netty.pipeline.BossHandler;
import kr.msleague.bcsp.internal.netty.pipeline.PacketDecoder;
import kr.msleague.bcsp.internal.netty.pipeline.PacketEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class BungeeComsoServerBootStrap {
    private static EventLoopGroup group = new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("BCSB IO Thread #%1$d").build());

    static {
        Direction.INBOUND.registerPacket(0x9000, HandShakePacket.class, HandShakePacket::new);
        Direction.OUTBOUND.registerPacket(0x9000, HandShakePacket.class, HandShakePacket::new);

        Direction.INBOUND.registerPacket(0x9001, PingPongPacket.class, PingPongPacket::new);
        Direction.OUTBOUND.registerPacket(0x9001, PingPongPacket.class, PingPongPacket::new);
    }

    public static CompletableFuture<Channel> initClient(boolean isBootup) {
        if (isBootup) {
            Direction.INBOUND.registerPacket(0x9003, ShutdownPacket.class, ShutdownPacket::new);
            Direction.OUTBOUND.registerPacket(0x9002, RelayingPacket.class, RelayingPacket::new);
            Direction.INBOUND.addListener(PingPongPacket.class, (packet, wrapper) -> {
                if (packet.getRecievedTime() == -1L) {
                    AbstractPacket rt = new PingPongPacket(packet.getTime(), System.currentTimeMillis());
                    rt.setCallBackResult(true);
                    wrapper.startCallBack(rt, PingPongPacket.class, rtPack -> wrapper.getPingCalculator().processPing(System.currentTimeMillis() - rtPack.getRecievedTime()));
                }
            });
        }
        CompletableFuture<Channel> future = new CompletableFuture<>();
        Bootstrap bs = new Bootstrap();
        bs.channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast("encode-filter", new LengthFieldPrepender(8))
                                .addLast("decode-filter", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8))
                                .addLast("packet-decoder", new PacketDecoder())
                                .addLast("packet-encoder", new PacketEncoder())
                                .addLast("handler-boss", new BossHandler(channel));
                    }
                })
                .group(group)
                .connect(new InetSocketAddress(GlobalProperties.getProperties("netty.targetServer.address"), Integer.parseInt(GlobalProperties.getProperties("netty.targetServer.port"))))
                .addListener((ChannelFutureListener) cfc -> {
                    if (cfc.isSuccess()) {
                        future.complete(cfc.channel());
                    } else {
                        future.completeExceptionally(cfc.cause());
                    }
                });
        return future;
    }

    public static CompletableFuture<Channel> initServer() {
        Direction.INBOUND.registerPacket(0x9002, RelayingPacket.class, RelayingPacket::new);
        Direction.OUTBOUND.registerPacket(0x9003, ShutdownPacket.class, ShutdownPacket::new);
        Direction.INBOUND.addListener(PingPongPacket.class, (packet, wrapper) -> {
            //Proxy->Bukkit->Proxy last->curr == bungeeping
            wrapper.getChannel().writeAndFlush(new PingPongPacket(packet.getTime(), -1L));

        });
        CompletableFuture<Channel> future = new CompletableFuture<>();
        ServerBootstrap sb = new ServerBootstrap();
        sb.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                        channel.pipeline()
                                .addLast("encode-filter", new LengthFieldPrepender(8))
                                .addLast("decode-filter", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 8, 0, 8))
                                .addLast("packet-decoder", new PacketDecoder())
                                .addLast("packet-encoder", new PacketEncoder())
                                .addLast("handler-boss", new BossHandler(channel));
                    }
                })
                .localAddress(GlobalProperties.getProperties("netty.server.address"), Integer.parseInt(GlobalProperties.getProperties("netty.server.port")))
                .group(group)
                .bind()
                .addListener((ChannelFutureListener) cf -> {
                    if (cf.isSuccess()) {
                        future.complete(cf.channel());
                    } else {
                        future.completeExceptionally(cf.cause());
                    }
                });
        return future;
    }
}

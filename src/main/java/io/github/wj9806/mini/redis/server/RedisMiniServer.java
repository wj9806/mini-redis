package io.github.wj9806.mini.redis.server;

import io.github.wj9806.mini.redis.server.core.RedisCore;
import io.github.wj9806.mini.redis.server.core.RedisCoreImpl;
import io.github.wj9806.mini.redis.server.handler.RespCommandHandler;
import io.github.wj9806.mini.redis.server.handler.RespDecoder;
import io.github.wj9806.mini.redis.server.handler.RespEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class RedisMiniServer implements RedisServer {

    private static final int DEFAULT_DB_NUM = 16;

    private String host;

    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private EventExecutorGroup commandExecutor;
    private Channel serverChannel;
    private RedisCore redisCore;
    private RespCommandHandler commandHandler;

    public RedisMiniServer(String host, int port) {
        this.host = host;
        this.port = port;
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        this.commandExecutor = new DefaultEventExecutorGroup(1, new DefaultThreadFactory("redis-cmd"));
        this.redisCore = new RedisCoreImpl(DEFAULT_DB_NUM);
        this.commandHandler = new RespCommandHandler(redisCore);
    }

    @Override
    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RespDecoder());
                        pipeline.addLast(commandExecutor, commandHandler);
                        pipeline.addLast(new RespEncoder());
                    }
                });

        try {
            serverChannel = serverBootstrap.bind(new InetSocketAddress(host, port)).sync().channel();
            log.info("RedisMiniServer started at " + host + ":" + port);
        } catch (InterruptedException e) {
            log.error("RedisMiniServer start error", e);
            stop();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void stop() {
        try {
            if (serverChannel != null) {
                serverChannel.close().sync();
            }

            if (workerGroup != null) {
                workerGroup.shutdownGracefully().sync();
            }

            if (bossGroup != null) {
                bossGroup.shutdownGracefully().sync();
            }
        } catch (InterruptedException e) {
            log.error("RedisMiniServer stop error", e);
            Thread.currentThread().interrupt();
        }
    }
}

package io.github.wj9806.mini.redis.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class StringHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("StringHandler read: " + msg);
        ctx.channel().writeAndFlush("+OK\r\n");
    }
}

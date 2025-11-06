package io.github.wj9806.mini.redis.server.handler;

import io.github.wj9806.mini.redis.protocal.Resp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RespEncoder extends MessageToByteEncoder<Resp> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Resp resp, ByteBuf out) throws Exception {
        try {
            resp.encode(out);
        } catch (Exception e) {
            log.error("encode error", e);
            ctx.channel().close();
        }
    }
}

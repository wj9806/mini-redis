package io.github.wj9806.mini.redis.server.handler;

import io.github.wj9806.mini.redis.protocal.Resp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RespDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            if (in.readableBytes() > 0) {
                in.markReaderIndex();
            }

            if (in.readableBytes() < 4) {
                return;
            }

            try {
                Resp resp = Resp.decode(in);
                if (resp != null) {
                    out.add(resp);
                }
            } catch (Exception e) {
                log.error("decode error", e);
                in.resetReaderIndex();
            }
        } catch (Exception e) {
            log.error("decode error", e);
        }
    }
}

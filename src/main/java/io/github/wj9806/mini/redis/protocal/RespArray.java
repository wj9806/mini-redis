package io.github.wj9806.mini.redis.protocal;

import io.netty.buffer.ByteBuf;

public class RespArray extends Resp {

    private Resp[] content;

    public RespArray(Resp[] content) {
        this.content = content;
    }

    public Resp[] getContent() {
        return content;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte('*');
        buffer.writeBytes(String.valueOf(content.length).getBytes());

        for (Resp r : content) {
            r.encode(buffer);
        }

        buffer.writeBytes(Resp.CR_LF);
    }
}

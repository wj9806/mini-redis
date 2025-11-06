package io.github.wj9806.mini.redis.protocal;

import io.netty.buffer.ByteBuf;

public class SimpleString extends Resp{

    private String content;

    public SimpleString(String content) {
        this.content = content;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte('+');
        buffer.writeBytes(content.getBytes());
        buffer.writeBytes(Resp.CR_LF);
    }
}

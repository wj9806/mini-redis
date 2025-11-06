package io.github.wj9806.mini.redis.protocal;

import io.netty.buffer.ByteBuf;

public class Errors extends Resp{

    private String err;

    public Errors(String err) {
        this.err = err;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte('-');
        buffer.writeBytes(err.getBytes());
        buffer.writeBytes(Resp.CR_LF);
    }
}

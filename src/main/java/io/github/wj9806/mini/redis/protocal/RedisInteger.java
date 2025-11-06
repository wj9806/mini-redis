package io.github.wj9806.mini.redis.protocal;

import io.netty.buffer.ByteBuf;

public class RedisInteger extends Resp {

    private int number;

    public RedisInteger(int num) {
        this.number = num;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(':');
        buffer.writeBytes(String.valueOf(number).getBytes());
        buffer.writeBytes(Resp.CR_LF);
    }
}

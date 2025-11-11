package io.github.wj9806.mini.redis.protocal;

import io.github.wj9806.mini.redis.structure.RedisBytes;
import io.netty.buffer.ByteBuf;

public class BulkString extends Resp {

    public static final byte[] NULL = "-1\r\n".getBytes();
    public static final byte[] EMPTY = "0\r\n\r\n".getBytes();
    private RedisBytes content;

    public BulkString(byte[] content) {
        this.content = new RedisBytes(content);
    }

    public BulkString(RedisBytes content) {
        this.content = content;
    }

    public RedisBytes getContent() {
        return content;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte('$');
        if (content == null) {
            buffer.writeBytes(NULL);
        } else {
            int len = content.getBytes().length;
            if (len == 0) {
                buffer.writeBytes(EMPTY);
            } else {
                buffer.writeBytes(String.valueOf(len).getBytes());
                buffer.writeBytes(Resp.CR_LF);
                buffer.writeBytes(content.getBytes());
                buffer.writeBytes(Resp.CR_LF);
            }
        }
    }
}

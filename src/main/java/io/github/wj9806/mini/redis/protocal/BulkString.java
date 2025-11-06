package io.github.wj9806.mini.redis.protocal;

import io.netty.buffer.ByteBuf;

public class BulkString extends Resp {

    private static final byte[] NULL = new byte[]{'-', '1', '\r', '\n'};
    private static final byte[] EMPTY = new byte[]{'0', '\r', '\n', '\r', '\n'};
    private byte[] content;

    public BulkString(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte('$');
        if (content == null) {
            buffer.writeBytes(NULL);
        } else {
            int len = content.length;
            if (len == 0) {
                buffer.writeBytes(EMPTY);
            } else {
                buffer.writeBytes(String.valueOf(len).getBytes());
                buffer.writeBytes(content);
                buffer.writeBytes(Resp.CR_LF);
            }
        }
    }
}

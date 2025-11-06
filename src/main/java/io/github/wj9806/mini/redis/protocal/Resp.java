package io.github.wj9806.mini.redis.protocal;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Resp {

    public static final byte[] CR_LF = new byte[]{13, 10};

    /**
     * SimpleString     +OK\r\n
     * Errors           -Error message\r\n
     * RedisInteger     :1000\r\n
     * BulkString       $6\r\nfoobar\r\n
     * RespArray        *2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n
     */
    public static Resp decode(ByteBuf buffer) {
        if (buffer.readableBytes() <= 0) {
            throw new RuntimeException("not a complete command");
        }

        char c = (char) buffer.readByte();
        switch (c) {
            case '+':
                return new SimpleString(getString(buffer));
            case '-':
                return new Errors(getString(buffer));
            case ':':
                return new RedisInteger(getNumber(buffer));
            case '$':
                int len = getNumber(buffer);
                if (buffer.readableBytes() < len + 2) {
                    throw new RuntimeException("not a complete command");
                }

                byte[] content;
                if (len == -1) {
                    content = null;
                } else {
                    content = new byte[len];
                    buffer.readBytes(content);
                }
                if (buffer.readByte() != '\r' || buffer.readByte() != '\n') {
                    throw new RuntimeException("not a complete command");
                }

                return new BulkString(content);
            case '*':
                int num = getNumber(buffer);
                Resp[] array = new Resp[num];
                for (int i = 0; i < num; i++) {
                    array[i] = decode(buffer);
                }
                return new RespArray(array);
            default:
                throw new RuntimeException("unknown command type");
        }
    }

    public abstract void encode(ByteBuf buffer);

    static String getString(ByteBuf buffer) {
        char c;
        StringBuilder result = new StringBuilder();
        while ((c = (char) buffer.readByte()) != '\r' && buffer.readableBytes() > 0)
            result.append(c);

        if (buffer.readableBytes() <=0 || (char) buffer.readByte() != '\n')
            throw new RuntimeException("not a complete command");

        return result.toString();
    }

    static int getNumber(ByteBuf buffer) {
        return Integer.parseInt(getString(buffer));
    }
}

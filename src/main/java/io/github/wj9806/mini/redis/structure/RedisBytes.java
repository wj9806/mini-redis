package io.github.wj9806.mini.redis.structure;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RedisBytes {

    private byte[] bytes;

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public RedisBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof RedisBytes rb) {
            return Arrays.equals(bytes, rb.bytes);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }
}

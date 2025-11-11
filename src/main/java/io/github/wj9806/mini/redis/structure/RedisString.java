package io.github.wj9806.mini.redis.structure;

import io.github.wj9806.mini.redis.internal.Sds;

public class RedisString implements RedisData {

    private volatile long timeout;
    private Sds value;

    public RedisString(Sds value) {
        this.value = value;
        this.timeout = -1;
    }

    @Override
    public long timeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public RedisBytes getValue() {
        return new RedisBytes(value.getBytes());
    }

    public void setSds(Sds value) {
        this.value = value;
    }

    public long incr() {
        try {
            long cur = Long.parseLong(value.toString());
            long newLong = cur + 1;
            value = new Sds(String.valueOf(newLong).getBytes());
            return cur;
        } catch (NumberFormatException e) {
            throw new IllegalStateException("value is not a number");
        }
    }
}

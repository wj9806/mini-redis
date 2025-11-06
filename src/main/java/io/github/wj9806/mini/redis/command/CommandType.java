package io.github.wj9806.mini.redis.command;

import io.github.wj9806.mini.redis.command.impl.Ping;
import io.github.wj9806.mini.redis.server.core.RedisCore;

import java.util.function.Function;

public enum CommandType {

    PING(redisCore -> new Ping());

    private final Function<RedisCore, Command> supplier;

    CommandType(Function<RedisCore, Command> supplier) {
        this.supplier = supplier;
    }

    public Function<RedisCore, Command> getSupplier() {
        return supplier;
    }
}

package io.github.wj9806.mini.redis.command.impl.string;

import io.github.wj9806.mini.redis.command.Command;
import io.github.wj9806.mini.redis.command.CommandType;
import io.github.wj9806.mini.redis.internal.Sds;
import io.github.wj9806.mini.redis.protocal.BulkString;
import io.github.wj9806.mini.redis.protocal.Resp;
import io.github.wj9806.mini.redis.protocal.SimpleString;
import io.github.wj9806.mini.redis.server.core.RedisCore;
import io.github.wj9806.mini.redis.structure.RedisBytes;
import io.github.wj9806.mini.redis.structure.RedisData;
import io.github.wj9806.mini.redis.structure.RedisString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Set implements Command {

    private RedisBytes key;
    private RedisBytes value;
    private RedisCore redisCore;

    public Set(RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    public CommandType getType() {
        return CommandType.SET;
    }

    @Override
    public void setContext(Resp[] array) {
        if (array.length < 3) {
            throw new IllegalArgumentException("SET command requires at least 3 arguments");
        }
        key = ((BulkString) array[1]).getContent();
        value = ((BulkString) array[2]).getContent();
    }

    @Override
    public Resp handle() {
        if (redisCore.get(key) != null) {
            RedisData data = redisCore.get(key);
            if (data instanceof RedisString redisString) {
                redisString.setSds(new Sds(value.getBytes()));
                log.debug("SET command updated existing key: {}",  new String(key.getBytes()));
                return new SimpleString("OK");
            }
        }
        log.debug("SET command added new key: {}", new String(key.getBytes()));
        redisCore.put(key, new RedisString(new Sds(value.getBytes())));
        return new SimpleString("OK");
    }
}

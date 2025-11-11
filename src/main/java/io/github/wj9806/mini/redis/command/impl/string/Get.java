package io.github.wj9806.mini.redis.command.impl.string;

import io.github.wj9806.mini.redis.command.Command;
import io.github.wj9806.mini.redis.command.CommandType;
import io.github.wj9806.mini.redis.protocal.BulkString;
import io.github.wj9806.mini.redis.protocal.Errors;
import io.github.wj9806.mini.redis.protocal.Resp;
import io.github.wj9806.mini.redis.server.core.RedisCore;
import io.github.wj9806.mini.redis.structure.RedisBytes;
import io.github.wj9806.mini.redis.structure.RedisData;
import io.github.wj9806.mini.redis.structure.RedisString;

import static io.github.wj9806.mini.redis.protocal.BulkString.NULL;

public class Get implements Command {

    private RedisCore redisCore;

    private RedisBytes key;

    public Get(RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    public CommandType getType() {
        return CommandType.GET;
    }

    @Override
    public void setContext(Resp[] array) {
        key = ((BulkString) array[1]).getContent();
    }

    @Override
    public Resp handle() {
        try {
            RedisData data = redisCore.get(key);
            if (data == null) return new BulkString(NULL);
            if (data instanceof RedisString redisString) {
                return new BulkString(redisString.getValue());
            }
            return new Errors("ERR operation against a key holding the wrong kind of value");
        } catch (Exception e) {
            return new Errors("ERR " + e.getMessage());
        }
    }
}

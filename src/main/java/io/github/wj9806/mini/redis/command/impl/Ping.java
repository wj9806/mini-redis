package io.github.wj9806.mini.redis.command.impl;

import io.github.wj9806.mini.redis.command.Command;
import io.github.wj9806.mini.redis.command.CommandType;
import io.github.wj9806.mini.redis.protocal.Resp;
import io.github.wj9806.mini.redis.protocal.SimpleString;

public class Ping implements Command {
    @Override
    public CommandType getType() {
        return CommandType.PING;
    }

    @Override
    public void setContext(Resp[] array) {

    }

    @Override
    public Resp handle() {
        return new SimpleString("PONG");
    }
}

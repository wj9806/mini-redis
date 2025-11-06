package io.github.wj9806.mini.redis.command;

import io.github.wj9806.mini.redis.protocal.Resp;

public interface Command {

    CommandType getType();

    void setContext(Resp[] array);

    Resp handle();

}

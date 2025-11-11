package io.github.wj9806.mini.redis.server.core;

import io.github.wj9806.mini.redis.structure.RedisBytes;
import io.github.wj9806.mini.redis.structure.RedisData;

import java.util.Set;

public interface RedisCore {

    Set<RedisBytes> keys();

    void put(RedisBytes key, RedisData value);

    RedisData get(RedisBytes key);

    long remove(RedisBytes key);

    void selectDB(int dbIndex);

    int getDBNum();

    int getCurrentDBIndex();
}

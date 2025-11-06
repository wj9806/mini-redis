package io.github.wj9806.mini.redis.server.core;

import io.github.wj9806.mini.redis.structure.RedisData;

import java.util.Set;

public interface RedisCore {

    Set<byte[]> keys();

    void put(byte[] key, RedisData value);

    RedisData get(byte[] key);

    long remove(byte[] key);

    void selectDB(int dbIndex);

    int getDBNum();

    int getCurrentDBIndex();
}

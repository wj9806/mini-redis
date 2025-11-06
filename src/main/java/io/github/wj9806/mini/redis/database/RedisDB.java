package io.github.wj9806.mini.redis.database;


import io.github.wj9806.mini.redis.internal.Dict;
import io.github.wj9806.mini.redis.structure.RedisData;

import java.util.Set;

public class RedisDB {

    private final Dict<byte[], RedisData> data;

    private final int id;

    public RedisDB(int id) {
        this.data = new Dict<>();
        this.id = id;
    }

    public Set<byte[]> keys() {
        return data.keySet();
    }

    public boolean exists(byte[] key) {
        return data.containsKey(key);
    }

    public RedisData get(byte[] key) {
        return data.get(key);
    }

    public void put(byte[] key, RedisData value) {
        data.put(key, value);
    }

    public RedisData remove(byte[] key) {
        return data.remove(key);
    }

    public void clear() {
        data.clear();
    }

    public int size() {
        return data.size();
    }
}

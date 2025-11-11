package io.github.wj9806.mini.redis.server.core;

import io.github.wj9806.mini.redis.database.RedisDB;
import io.github.wj9806.mini.redis.structure.RedisBytes;
import io.github.wj9806.mini.redis.structure.RedisData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedisCoreImpl implements RedisCore {

    private final int dbNum;

    private final List<RedisDB> databases;

    private int currentDBIndex = 0;

    public RedisCoreImpl(int dbNum) {
        this.databases = new ArrayList<>(dbNum);
        for (int i = 0; i < dbNum; i++) {
            databases.add(new RedisDB(i));
        }
        this.dbNum = dbNum;
    }

    @Override
    public Set<RedisBytes> keys() {
        RedisDB db = databases.get(currentDBIndex);
        return db.keys();
    }

    @Override
    public void put(RedisBytes key, RedisData value) {
        RedisDB db = databases.get(currentDBIndex);
        db.put(key, value);
    }

    @Override
    public RedisData get(RedisBytes key) {
        RedisDB db = databases.get(currentDBIndex);
        return db.get(key);
    }

    @Override
    public long remove(RedisBytes key) {
        RedisDB db = databases.get(currentDBIndex);
        RedisData data = db.remove(key);
        return data != null ? 1 : 0;
    }

    @Override
    public void selectDB(int dbIndex) {
        if (dbIndex < 0 || dbIndex >= dbNum) {
            throw new IllegalArgumentException("Invalid DB index: " + dbIndex);
        }
        currentDBIndex = dbIndex;
    }

    @Override
    public int getDBNum() {
        return dbNum;
    }

    @Override
    public int getCurrentDBIndex() {
        return currentDBIndex;
    }
}

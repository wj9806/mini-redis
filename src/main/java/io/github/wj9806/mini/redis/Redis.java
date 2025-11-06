package io.github.wj9806.mini.redis;

import io.github.wj9806.mini.redis.server.RedisMiniServer;
import io.github.wj9806.mini.redis.server.RedisServer;

public class Redis {

    public static void main(String[] args) {
        RedisServer redisServer = new RedisMiniServer("localhost", 6379);
        redisServer.start();
    }
    
}

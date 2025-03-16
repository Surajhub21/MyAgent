package com.developersuraj.MyAgent.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class MemoryAgent {

    private final Jedis jedis;

    @Autowired
    public MemoryAgent(Jedis jedis) { // Inject Jedis from RedisConfig
        this.jedis = jedis;
    }

    private static final long EXPIRY_TIME = 3600; // 1 hour

    public String getCachedResponse(String query) {
        return jedis.get(query);
    }

    public void cacheResponse(String query, String response) {
        jedis.set(query, response);
        jedis.expire(query, EXPIRY_TIME);
    }
}
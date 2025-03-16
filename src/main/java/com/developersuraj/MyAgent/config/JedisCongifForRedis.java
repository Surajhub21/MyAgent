package com.developersuraj.MyAgent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisCongifForRedis {

    @Value("${REDIS_HOST}")
    private String redisHost;

    @Value("${REDIS_PORT}")
    private int redisPort;

    @Value("${REDIS_PASSWORD}")
    private String redisPassword;

    @Bean
    public Jedis jedis() {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisPassword);
        return jedis;
    }
}


package com.ssm.demo.cache;

import org.springframework.data.redis.connection.jedis.JedisConnection;

@FunctionalInterface
public interface RedisCallback {
  Object doWithRedis(JedisConnection jedisConn);
}

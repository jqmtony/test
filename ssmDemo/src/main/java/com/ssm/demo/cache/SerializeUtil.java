package com.ssm.demo.cache;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

public final class SerializeUtil {

  private static final GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

  private SerializeUtil() {
  }

  public static byte[] serialize(Object object) {
    return serializer.serialize(object);
  }

  public static Object unserialize(byte[] bytes) {
    return serializer.deserialize(bytes);
  }

}
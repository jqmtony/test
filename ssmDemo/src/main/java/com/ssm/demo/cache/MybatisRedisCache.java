package com.ssm.demo.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.ssm.demo.util.SpringHelper;


/**
 * MyBatis的Redis缓存实现, 使用时在Mapper.xml文件中添加
 * <p>
 * &lt;cache type="com.ssm.demo.cache.MyBatisRedisCache"/&gt;
 * </p>
 *
 * @author
 */
public class MybatisRedisCache implements Cache {

  /**
   * The ReadWriteLock.
   */
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  /**
   * cache标识
   */
  private String id;

  public MybatisRedisCache(String id) {
    this.id = id;
  }

  private Object execute(RedisCallback callback) {
    JedisConnection jedisConn = getJedisConnectionFactory().getConnection();
    try {
      return callback.doWithRedis(jedisConn);
    } finally {
      jedisConn.close();
    }
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public int getSize() {
    return (Integer) execute(jedisConn -> jedisConn.hGetAll(id.getBytes()).size());
  }

  @Override
  public void putObject(final Object key, final Object value) {
    execute(jedisConn -> jedisConn.hSet(id.getBytes(), key.toString().getBytes(), SerializeUtil.serialize(value)));
  }

  @Override
  public Object getObject(final Object key) {
    return execute(jedisConn -> SerializeUtil.unserialize(jedisConn.hGet(id.getBytes(), key.toString().getBytes())));
  }

  @Override
  public Object removeObject(final Object key) {
    return execute(jedisConn -> jedisConn.hDel(id.getBytes(), key.toString().getBytes()));
  }

  @Override
  public void clear() {
    execute(jedisConn -> jedisConn.del(id.getBytes()));
  }

  @Override
  public ReadWriteLock getReadWriteLock() {
    return readWriteLock;
  }

  @Override
  public String toString() {
    return "Redis {" + id + "}";
  }

  private JedisConnectionFactory getJedisConnectionFactory() {
    return SpringHelper.getBean(JedisConnectionFactory.class);
  }
}

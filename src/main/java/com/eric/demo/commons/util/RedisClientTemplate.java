package com.eric.demo.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liumy on 2017/7/16.
 */
public class RedisClientTemplate {

    private static final Logger log = LoggerFactory.getLogger(RedisClientTemplate.class);

    private ShardedJedisPool shardedJedisPool;

    private String prefixed = "HORE-";

    public RedisClientTemplate(ShardedJedisPool shardedJedisPool, String prefixed){
        this.shardedJedisPool = shardedJedisPool;
        this.prefixed = prefixed;
    }
    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public String getPrefixed() {
        return prefixed;
    }

    public void setPrefixed(String prefixed) {
        this.prefixed = prefixed;
    }

    public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("getRedisClent error", e);
        }
        return null;
    }

    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    public void disconnect() {
        ShardedJedis shardedJedis = getRedisClient();
        shardedJedis.disconnect();
    }

    /**
     * 往redis里放入值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        String result = null;
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 往redis里放入值并设置有效期
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set(String key, String value, int seconds) {
        String result = null;
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.set(key, value);
            shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 从redis里获取值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }

        boolean broken = false;
        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean exists(String key) {
        key = prefixed + key;
        Boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String type(String key) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.type(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 设置过期
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.expire(key, seconds);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     *设置过期
     *
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireAt(String key, long unixTime) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.expireAt(key, unixTime);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long ttl(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.ttl(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public boolean setbit(String key, long offset, boolean value) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        boolean result = false;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public boolean getbit(String key, long offset) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        boolean result = false;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;

        try {
            result = shardedJedis.getbit(key, offset);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public long setrange(String key, long offset, String value) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        long result = 0;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setrange(key, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String getrange(String key, long startOffset, long endOffset) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getrange(key, startOffset, endOffset);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String getSet(String key, String value) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getSet(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long setnx(String key, String value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setnx(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String setex(String key, int seconds, String value) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.setex(key, seconds, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long decrBy(String key, long integer) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.decrBy(key, integer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long decr(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.decr(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long incrBy(String key, long integer) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.incrBy(key, integer);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long incr(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.incr(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long append(String key, String value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.append(key, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String substr(String key, int start, int end) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.substr(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hset(String key, String field, String value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hset(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String hget(String key, String field) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hget(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hsetnx(String key, String field, String value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hsetnx(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String hmset(String key, Map<String, String> hash) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hmset(key, hash);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> hmget(String key, String... fields) {
        key = prefixed + key;
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hmget(key, fields);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hincrBy(String key, String field, long value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hincrBy(key, field, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean hexists(String key, String field) {
        key = prefixed + key;
        Boolean result = false;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hexists(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long del(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.del(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hdel(String key, String field) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hdel(key, field);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long hlen(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hlen(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> hkeys(String key) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hkeys(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> hvals(String key) {
        key = prefixed + key;
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hvals(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Map<String, String> hgetAll(String key) {
        key = prefixed + key;
        Map<String, String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.hgetAll(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    // ================list ==========================
    public Long rpush(String key, String string) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.rpush(key, string);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long lpush(String key, String string) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lpush(key, string);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long llen(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.llen(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> lrange(String key, long start, long end) {
        key = prefixed + key;
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lrange(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String ltrim(String key, long start, long end) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.ltrim(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String lindex(String key, long index) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lindex(key, index);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String lset(String key, long index, String value) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lset(key, index, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long lrem(String key, long count, String value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lrem(key, count, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String lpop(String key) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.lpop(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String rpop(String key) {
        key = prefixed + key;
        String result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.rpop(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    //return 1 add a not exist value ,
    //return 0 add a exist value
    public Long sadd(String key, String member) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.sadd(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> smembers(String key) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.smembers(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long srem(String key, String member) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();

        Long result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.srem(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String spop(String key) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.spop(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long scard(String key) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        Long result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.scard(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Boolean sismember(String key, String member) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        Boolean result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.sismember(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String srandmember(String key) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.srandmember(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zadd(String key, double score, String member) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.zadd(key, score, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrange(String key, int start, int end) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.zrange(key, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrem(String key, String member) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.zrem(key, member);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Double zincrby(String key, double score, String member) {
        key = prefixed + key;
        Double result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zincrby(key, score, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrank(String key, String member) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrank(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zrevrank(String key, String member) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrank(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrange(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeWithScores(String key, int start, int end) {
        key = prefixed + key;
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeWithScores(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
        key = prefixed + key;
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeWithScores(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zcard(String key) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zcard(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Double zscore(String key, String member) {
        key = prefixed + key;
        Double result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zscore(key, member);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> sort(String key) {
        key = prefixed + key;
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sort(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<String> sort(String key, SortingParams sortingParameters) {
        key = prefixed + key;
        List<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.sort(key, sortingParameters);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zcount(String key, double min, double max) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zcount(key, min, max);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScore(key, min, max);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScore(key, max, min);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScore(key, min, max, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        key = prefixed + key;
        Set<String> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScore(key, max, min, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        key = prefixed + key;
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrangeByScoreWithScores(key, min, max);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        key = prefixed + key;
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }


    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        key = prefixed + key;
        Set<Tuple> result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zremrangeByRank(String key, int start, int end) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zremrangeByRank(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long zremrangeByScore(String key, double start, double end) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.zremrangeByScore(key, start, end);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        key = prefixed + key;
        Long result = null;
        ShardedJedis shardedJedis = getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {

            result = shardedJedis.linsert(key, where, pivot, value);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline) {
        ShardedJedis shardedJedis = getRedisClient();
        List<Object> result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.pipelined(shardedJedisPipeline);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Jedis getShard(String key) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        Jedis result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getShard(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public JedisShardInfo getShardInfo(String key) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        JedisShardInfo result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getShardInfo(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public String getKeyTag(String key) {
        key = prefixed + key;
        ShardedJedis shardedJedis = getRedisClient();
        String result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getKeyTag(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Collection<JedisShardInfo> getAllShardInfo() {
        ShardedJedis shardedJedis = getRedisClient();
        Collection<JedisShardInfo> result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getAllShardInfo();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

    public Collection<Jedis> getAllShards() {

        ShardedJedis shardedJedis = getRedisClient();
        Collection<Jedis> result = null;
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.getAllShards();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            returnResource(shardedJedis, broken);
        }
        return result;
    }

}

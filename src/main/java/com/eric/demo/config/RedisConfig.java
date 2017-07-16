package com.eric.demo.config;

import com.eric.demo.commons.util.RedisClientTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumy on 2017/7/16.
 */
@Configuration
@PropertySource("classpath:redis/redis-dev.properties")
@Profile("dev")
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;

    @Value("${redis.pool.maxActive}")
    private int maxTotal;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.ip}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.prefix}")
    private String prefix;

    @Value("${redis.pool.maxwait}")
    private long maxWaitMillis;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.app}")
    private String appName;

    @Bean(name="redisClient")
    public RedisClientTemplate getRedisClientTemplate(){
        LOGGER.info("初始化redis："+appName);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        JedisShardInfo shardInfo = new JedisShardInfo(host, port);
        List<JedisShardInfo> shardInfoList = new ArrayList<>();
        shardInfoList.add(shardInfo);
        ShardedJedisPool jedisPool = new ShardedJedisPool(jedisPoolConfig, shardInfoList);

        RedisClientTemplate redisClient = new RedisClientTemplate(jedisPool, prefix);
        return redisClient;
    }

}
package com.eric.demo.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eric.demo.commons.util.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis缓存通用模板封装
 * liumy
 */
public abstract class CommonCache<T, K> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public final static String Prefix = "tcc";

    //spring上下文货物redis操作模板
    @Autowired
    @Qualifier(value = "redisTemplate1")
    private RedisTemplate<String, Object> redisTemplate;

    //默认缓存五分钟
    private int time = 60 * 5;

    //是否使用缓存设置
    private Boolean useCache = true;

    protected K get(String keyPrefix, T key, Class<K> type, Fun<T, K> fun) throws Exception {
        //标记本次为在redis取数据超时，此时调用接口取数据
        K value = null;
        if (useCache) {
            try {
                String data = (String) redisTemplate.opsForValue().get(Prefix + keyPrefix + "-" + JSON.toJSONString(key));
                value = JSONObject.parseObject(data, type);
                if (Check.NuNObj(value)) {
                    value = fun.get(key);
                } else {
                    logger.info("【本次获取数据在redis缓存取值->参数" + JSONObject.toJSONString(key) + "】");
                    return value;
                }
            } catch (Exception e) {
                //重试
                value = tryReq(key, fun, value);
                logger.error("获取数据异常", e);
            }
            try {
                redisTemplate.opsForValue().set(Prefix + keyPrefix + "-" + JSONObject.toJSONString(key), JSONObject.toJSONString(value), time, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.error("数据存入redis缓存异常", e);
            }
        } else {
            try {
                return fun.get(key);
            } catch (Exception e) {
                logger.error("不走缓存获取数据错误，开始重试...", e);
                return tryReq(key, fun, value);
            }
        }
        return value;
    }

    private K tryReq(T key, Fun<T, K> fun, K value) {
        int tryTime = 0;
        boolean flag = false;
        do {
            try {
                value = fun.get(key);
                flag = true;
            } catch (Exception e1) {
                logger.error("访问百度地图重试异常次数：" + tryTime, e1);
            } finally {
                tryTime++;
            }
        } while (tryTime < 3 && !flag);
        return value;
    }

    public static abstract class Fun<T, K> {
        public abstract K get(T key) throws Exception;

    }

    public abstract K execute(T key) throws Exception;

    public boolean clearByKey(T key) {
        redisTemplate.delete(JSON.toJSONString(key));
        return true;
    }

    public boolean clearByKeyPrefix(String key) {
        redisTemplate.delete(redisTemplate.keys(Prefix + key));
        return true;
    }

    protected Boolean getUseCache() {
        return useCache;
    }

    protected void setUseCache(Boolean useCache) {
        this.useCache = useCache;
    }

    protected int getTime() {
        return time;
    }

    protected void setTime(int time) {
        this.time = time;
    }
}

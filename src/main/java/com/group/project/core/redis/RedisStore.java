package com.group.project.core.redis;

import com.group.project.core.common.SpringContextUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisStore {
    private static RedisTemplate<String, Object> redisTemplate = (RedisTemplate)
            SpringContextUtil.getBean("redisTemplate");

    public static <E> Set<E> keys(String pattern) {
        return (Set<E>) redisTemplate.keys(pattern);
    }

    public static void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void setValue(String key, Object value, long timeout, TimeUnit timeType) {
        redisTemplate.opsForValue().set(key, value);
        expire(key, timeout, timeType);
    }

    public static void setMapValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static void setMapValue(String key, String hashKey, Object value, long timeout, TimeUnit timeType) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        expire(key, timeout, timeType);
    }

    public static <T> T getValue(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public static <T> T getMapValue(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    public static void delMapValue(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, new Object[]{hashKey});
    }

    public static void delValue(String key) {
        redisTemplate.delete(key);
    }

    public static void expire(String key, long timeout, TimeUnit timeType) {
        redisTemplate.expire(key, timeout, timeType);
    }

    public static boolean hasKey(String key) {
        return redisTemplate.hasKey(key).booleanValue();
    }

    public static boolean zsetAadd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score).booleanValue();
    }

    public static Long zsetDel(String key, Object... value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    public static Long zsetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public static Set zsetRange(String key, int start, int end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public static Set zsetRevRange(String key, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public static <V> Long rpush(String key, V value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public static Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public static <V> List<V> lrange(String key, int start, int end) {
        return (List<V>) redisTemplate.opsForList().range(key, start, end);
    }

    public static <V> V lpop(String key) {
        return (V) redisTemplate.opsForList().leftPop(key);
    }

    public static <V> V lremove(String key, V value) {
        return (V) redisTemplate.opsForList().remove(key, 0L, value);
    }
}

package com.jerry.webappdemojpa.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置字符串值到Redis
     *
     * @param key   键
     * @param value 值
     * @param timeout 超时时间（秒），如果为null则不设置过期时间
     * @return 是否成功
     */
    public boolean set(String key, String value, Long timeout) {
        try {
            if (timeout != null && timeout > 0) {
                stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            } else {
                stringRedisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to set value in Redis: " + e.getMessage(), e);
        }
    }

    /**
     * 从Redis获取字符串值
     *
     * @param key 键
     * @return 值，如果键不存在返回null
     */
    public String get(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get value from Redis: " + e.getMessage(), e);
        }
    }

    /**
     * 删除键
     *
     * @param key 键
     * @return 是否成功删除
     */
    public boolean delete(String key) {
        try {
            return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete key from Redis: " + e.getMessage(), e);
        }
    }

    /**
     * 检查键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean exists(String key) {
        try {
            return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
        } catch (Exception e) {
            throw new RuntimeException("Failed to check key existence in Redis: " + e.getMessage(), e);
        }
    }


}

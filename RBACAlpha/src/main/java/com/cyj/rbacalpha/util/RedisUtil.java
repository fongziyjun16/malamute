package com.cyj.rbacalpha.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setWithExpiration(String key, Object value, long second) {
        set(key, value);
        redisTemplate.expire(key, second, TimeUnit.SECONDS);
    }

    public <V> V get(String key, Class<V> toValueType) {
        return new ObjectMapper().convertValue(redisTemplate.opsForValue().get(key), toValueType);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}

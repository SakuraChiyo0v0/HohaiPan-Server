package com.hhu.utils;

import cn.hutool.json.JSONUtil;
import com.hhu.result.RedisData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HHURedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 将指定的键值对设置到Redis中，并指定过期时间
     * @param key 键，用于标识存储在Redis中的值
     * @param value 值，可以是任何对象，将被转换为JSON字符串存储
     * @param time 过期时间，表示值在Redis中保存的时长
     * @param unit 时间单位，用于解释time参数的时间粒度，例如秒、分钟等
     */
    public void set(String key, Object value, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    /**
     * 使用逻辑过期时间将值存储到Redis中
     * @param key Redis中存储数据的键
     * @param value 要存储的值
     * @param time 数据的过期时间长度
     * @param unit 过期时间的单位
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 写入Redis
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

}

package com.drinks.milk.utils;

import com.alibaba.fastjson.JSON;
//import com.insuranceman.chaos.enums.ChaosLockModuleEnum;
import com.drinks.milk.enums.ChaosLockModuleEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>Title: RedisUtils</p>
 *
 * @author hupenglong
 * @date 2020-06-22 17:24
 */
@Component
public class RedisUtils {
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 登录过期时长，单位：秒
     */
    public final static long LOGIN_EXPIRE = 60 * 60 * 24 * 15;
    /**
     * 验证码过期时间
     */
    public final static long VERIFICATIONCODE_EXPIRE = 3;
    /**
     * 在线入司验证码过期时间 （30分钟过期），单位：秒
     */
    public final static long JOIN_COMPANY_EXPIRE = 60 * 5;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;
    public static final String PREFIX = "CHAOS:";
    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean set(String key, Object value, int expiry) {
        setObjectWithExpire(PREFIX + key, value, expiry);
        return true;
    }

    public boolean set(String key, Object value) {
        set(key, JSON.toJSONString(value));
        return true;
    }

    public void setObjectWithExpire(String key, Object o, Integer expire) {
        if (expire != NOT_EXPIRE) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(o), expire, TimeUnit.SECONDS);
        } else {
            set(key, o);
        }
    }

    public void setWithSecondsExpire(String key, String value, Integer expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    public boolean set(String prefix, String key, Object value, int expiry) {
        this.set(prefix + "_" + key, value, expiry);
        return true;
    }

    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(PREFIX + key, value);
            return true;
        } catch (Exception e) {
            logger.error("Redis设置值报错：", e);
            return false;
        }
    }

    public Object get(String prefix, String key) {
        return this.get(prefix + "_" + key);
    }

    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(PREFIX + key);
    }

    /**
     * 巨坑：新增不加前缀的get，因为setWithSecondsExpire没加前缀
     * @param:
     * @return:
     * @author: Guo kaixuan
     * @date: 2020/11/28
     * @time: 16:03
     */
    public String getWithNoPrefix(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public void delKey(String key) {
        redisTemplate.delete(key);
    }

    public void delete(String table, String key) {
        if (table != null && key != null) {
            if (table.trim().length() > 0) {
                this.delKey(table + "_" + key);
            }
        }
    }

    public boolean zadd(String key, String value, double score) {
        try {
            redisTemplate.opsForZSet().add(PREFIX + key, value, score);
            return true;
        } catch (Exception e) {
            logger.error("新增数据报错", e);
            return false;
        }
    }

    public Long zrank(String key, String value) {
        Long result = null;
        try {
            result = redisTemplate.opsForZSet().rank(PREFIX + key, value);
        } catch (Exception e) {
            logger.error("获取排名报错", e);
        }
        return result;
    }

    public void incr(String key) {
        redisTemplate.opsForValue().increment(key, 1);
    }


    public void expire(String key) {
        redisTemplate.expire(key, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 键前缀处理
     */
    private String getKey(String key) {
        return PREFIX + key;
    }

    /**
     * 加锁
     */
    public boolean lock(ChaosLockModuleEnum lockModuleEnum, String key) {
        key = this.getKey(lockModuleEnum.getKey(key));
        boolean lock = redisTemplate.opsForValue().setIfAbsent(key, "lock");
        if (lock || longEffective(key)){
            redisTemplate.expire(key,lockModuleEnum.getExpireTimeOut(), lockModuleEnum.getExpireTimeUnit());
        }
        return lock;
    }

    /**
     * 解锁
     */
    public void unLock(ChaosLockModuleEnum lockModuleEnum,String key) {
        this.delKey(this.getKey(lockModuleEnum.getKey(key)));
    }

    /**
     * 判断是否有锁
     */
    public boolean isLock(ChaosLockModuleEnum lockModuleEnum, String key) {
        key = this.getKey(lockModuleEnum.getKey(key));
        if (StringUtils.equals(this.get(key), "lock")) {
            return true;
        }
        return false;
    }

    /**
     * 是否长期有效
     */
    public boolean longEffective(String key) {
        return redisTemplate.getExpire(key) == -1;
    }

}

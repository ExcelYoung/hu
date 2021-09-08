package com.drinks.milk.configuration.redis;

import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.configuration.redis.redisson.DelayMessage;
import com.drinks.milk.configuration.redis.redisson.DelayQueueTask;
import com.drinks.milk.constant.CommonConstant;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * <p>Title: RedisConfig</p>
 * 初始化Redis配置。
 *
 */
@Configuration
public class RedisConfig {
    @Autowired
    private ConfigService configService;

    /**
     * 初始化RedisProperties
     *
     * @return RedisProperties
     */
    @Bean
    @Primary
    public RedisProperties redisProperties() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setHost(configService.getString(CommonConstant.Redis.REDIS_HOST));
        redisProperties.setPassword(configService.getString(CommonConstant.Redis.REDIS_PASSWORD));
        redisProperties.setPort(Integer.valueOf(configService.getString(CommonConstant.Redis.REDIS_PORT)));
        redisProperties.setTimeout(Duration.ofMillis(Long.valueOf(configService.getString(CommonConstant.Redis.REDIS_TIMEOUT))));
        redisProperties.setDatabase(Integer.valueOf(configService.getString(CommonConstant.Redis.REDIS_DATASOURCE)));
        return redisProperties;
    }


    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setConnectTimeout(10000);
        singleServerConfig
                .setAddress("redis://" + configService.getString(CommonConstant.Redis.REDIS_HOST)
                        + ":" + Integer.valueOf(configService.getString(CommonConstant.Redis.REDIS_PORT)));
        if (StringUtils.hasText(configService.getString(CommonConstant.Redis.REDIS_PASSWORD))) {
            singleServerConfig.setPassword(configService.getString(CommonConstant.Redis.REDIS_PASSWORD));
        }
        return Redisson.create(config);
    }

    @Bean
    public RBlockingQueue<DelayMessage> blockingFairQueue() {
        RedissonClient redisson = getRedisson();
        return redisson.getBlockingQueue(DelayQueueTask.DELAY_QUEUE_NAME);
    }


    @Bean
    public RDelayedQueue<DelayMessage> delayedQueue() {
        RedissonClient redisson = getRedisson();
        RBlockingQueue<DelayMessage> blockingFairQueue = blockingFairQueue();
        return redisson.getDelayedQueue(blockingFairQueue);
    }

}


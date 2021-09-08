package com.drinks.milk.configuration.redis.redisson;

/**
 * 实现此类执行具体的业务
 */
public interface DelayExecutor {
    void execute(DelayMessage delayJob);
}

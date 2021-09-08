package com.drinks.milk.configuration.redis.redisson;

import lombok.Data;

import java.util.Date;

/**
 * 延时队列消息
 */
@Data
public class DelayMessage {
    /**
     * 具体执行实例实现
     */
    private Class executeClass;


    /**
     * 消息内容
     */
    private String message;


    /**
     * 消息跳转参数
     */
    private String param;


    /**
     * 跳转地址
     */
    private String jumpUrl;


    /**
     * 用户id
     */
    private String userId;

    /**
     * 通知时间
     */
    private Date nofityTime;

}
package com.drinks.milk.configuration.rabbitmq;

import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.constant.CommonConstant;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * 初始化rabbitmq配置。
 */
@Configuration
public class RabbitMqConfig {
    @Autowired
    private ConfigService configService;

    @Bean
    @Primary
    public RabbitProperties rabbitProperties() {
        RabbitProperties rabbitProperties = new RabbitProperties();
        rabbitProperties.setHost(configService.getString(CommonConstant.RabbitMq.HOST));
        rabbitProperties.setPort(Integer.valueOf(configService.getString(CommonConstant.RabbitMq.PORT)));
        rabbitProperties.setUsername(configService.getString(CommonConstant.RabbitMq.USERNAME));
        rabbitProperties.setPassword(configService.getString(CommonConstant.RabbitMq.PASSWORD));
        rabbitProperties.setVirtualHost(configService.getString(CommonConstant.RabbitMq.VIRTUAL_HOST));
        RabbitProperties.SimpleContainer simple = rabbitProperties.getListener().getSimple();
        //设置并发
        simple.setConcurrency(Integer.valueOf(configService.getString(CommonConstant.RabbitMq.LISTENER_SIMPLE_CONCURRENCY)));
        simple.setMaxConcurrency(Integer.valueOf(configService.getString(CommonConstant.RabbitMq.LISTENER_SIMPLE_MAXCONCURRENCY)));
        //设置确认模式
        simple.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return rabbitProperties;
    }

}

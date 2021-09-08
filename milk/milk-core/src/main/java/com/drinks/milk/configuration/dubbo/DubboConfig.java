package com.drinks.milk.configuration.dubbo;

import com.alibaba.dubbo.config.*;
import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: DubboConfig</p>
 * 初始化dubbo配置
 *
 * @author hupenglong
 * @date 2020-06-22 15:35
 */
@Configuration
public class DubboConfig {
    @Autowired
    private ConfigService configService;

    /**
     * 初始化ApplicationConfig应用信息
     *
     * @return ApplicationConfig
     */
//    @Bean
    public ApplicationConfig getApplicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(configService.getString(CommonConstant.Dubbo.APPLICATION_NAME));
        applicationConfig.setEnvironment(configService.getString(CommonConstant.Dubbo.APPLICATION_ENV));
        return applicationConfig;
    }

    /**
     * 初始化RegistryConfig注册中心
     *
     * @return RegistryConfig
     */
    @Bean
    public RegistryConfig getRegistryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(configService.getString(CommonConstant.Dubbo.REGISTRY_ADDRESS));
        //registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setId(configService.getString(CommonConstant.Dubbo.REGISTRY_ID));
        //registryConfig.setRegister(false);
        registryConfig.setGroup(configService.getString(CommonConstant.Dubbo.REGISTRY_GROUP));
        return registryConfig;
    }

    /**
     * 初始化ProtocolConfig协议信息
     *
     * @return ProtocolConfig
     */
    @Bean
    public ProtocolConfig getProtocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(configService.getString(CommonConstant.Dubbo.PROTOCOL_NAME));
        protocolConfig.setPort(Integer.valueOf(configService.getString(CommonConstant.Dubbo.PROTOCOL_PORT)));
        protocolConfig.setDispatcher(configService.getString(CommonConstant.Dubbo.DISPATCHER));
        protocolConfig.setThreads(Integer.valueOf(configService.getString(CommonConstant.Dubbo.THREADS)));
        protocolConfig.setThreadpool(configService.getString(CommonConstant.Dubbo.THREAD_POOL));
        return protocolConfig;
    }

    /**
     * 初始化ConsumerConfig消费者配置
     *
     * @return ConsumerConfig
     */
    @Bean
    public ConsumerConfig getConsumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(Integer.valueOf(configService.getString(CommonConstant.Dubbo.CONSUMER_TIMEOUT)));
        return consumerConfig;
    }

    /**
     * 初始化ProviderConfig配置
     */
    @Bean
    public ProviderConfig getProviderConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setFilter("-exception,dubboExceptionFilter");
        return providerConfig;
    }
}

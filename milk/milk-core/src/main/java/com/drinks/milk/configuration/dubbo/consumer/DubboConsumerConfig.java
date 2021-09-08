package com.drinks.milk.configuration.dubbo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DubboConsumerConfig {
    @Autowired
    private RegistryConfig registryConfig;
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    protected ConfigService configService;

    protected void setReferenceConfig(ReferenceConfig rf, Class claz) {
        rf.setInterface(claz);
        rf.setGroup(configService.getString(CommonConstant.Dubbo.REGISTRY_GROUP));
        rf.setTimeout(Integer.valueOf(configService.getString(CommonConstant.Dubbo.CONSUMER_TIMEOUT)));
        applicationConfig.setName(configService.getString(CommonConstant.Dubbo.APPLICATION_NAME));
        applicationConfig.setEnvironment(configService.getString(CommonConstant.Dubbo.APPLICATION_ENV));
        rf.setApplication(applicationConfig);
        rf.setRegistry(registryConfig);
        rf.setCheck(false);
    }

    //此处省略多个Bean....
    /*@Bean
    public MessageService messageService(){
        ReferenceConfig<MessageService> config = new ReferenceConfig<>();
        setReferenceConfig(config, MessageService.class);
        return config.get();
    }*/
}

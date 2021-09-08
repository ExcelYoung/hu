package com.drinks.milk.configuration.dubbo.provider;

import com.alibaba.dubbo.config.*;
import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.constant.CommonConstant;
import com.drinks.milk.api.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <p>Title: DubboProviderTemplate</p>
 *
 * @author hupenglong
 * @date 2020-06-28 20:12
 */
@Component
public class DubboProviderConfig {
    @Autowired
    ConfigService configService;
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    ProtocolConfig protocolConfig;
    @Autowired
    RegistryConfig registryConfig;

    @Autowired
    DemoService demoService;

    @Autowired
    ProviderConfig providerConfig;


    //此处省略多个Bean....
    @Bean
    public ServiceConfig<DemoService> serviceConfig() {
        ServiceConfig<DemoService> config = new ServiceConfig<>();
        config.setInterface(DemoService.class);
        config.setRef(demoService);
        someRepate(config);
        return config;
    }


    private void someRepate(ServiceConfig<?> config) {
        config.setTimeout(Integer.valueOf(configService.getString(CommonConstant.Dubbo.CONSUMER_TIMEOUT)));
        config.setGroup(configService.getString(ConfigService.TEST_SERVICE_GROUP));
        applicationConfig.setName(configService.getString(CommonConstant.Dubbo.APPLICATION_NAME));
        applicationConfig.setEnvironment(configService.getString(CommonConstant.Dubbo.APPLICATION_ENV)); //注册到哪个文件夹
        config.setApplication(applicationConfig);
        config.setProtocol(protocolConfig);
        config.setRegistry(registryConfig);
        config.setProvider(providerConfig);
        config.export();
    }
}

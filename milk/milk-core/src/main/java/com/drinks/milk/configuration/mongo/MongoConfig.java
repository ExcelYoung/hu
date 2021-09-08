package com.drinks.milk.configuration.mongo;

import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.constant.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p>Title: MongoConfig</p>
 * 初始化mongo配置
 *
 * @author hupenglong
 * @date 2020-06-27 15:11
 */
@Configuration
public class MongoConfig {
    private static Logger logger = LoggerFactory.getLogger(ConfigService.class);
    @Autowired
    private ConfigService configService;

    /**
     * 初始化MongoProperties
     *
     * @return MongoProperties
     */
    @Bean
    @Primary
    public MongoProperties mongoProperties() {
        MongoProperties properties = new MongoProperties();
        properties.setUri(configService.getString(CommonConstant.Mongo.DATA_MONGO_URI));
        logger.info("uri:{}",properties.getUri());
        return properties;
    }
}

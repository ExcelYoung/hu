package com.drinks.milk.configuration;

import com.dangdang.config.service.zookeeper.ZookeeperConfigGroup;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigService {
    private static Logger logger = LoggerFactory.getLogger(ConfigService.class);
    @Autowired
    private ZookeeperConfigGroup configGroup;

    public static final String TEST_SERVICE_GROUP = "dubbo.registry.group";

    /**
     * 从zk上获取节点存储的数据
     *
     * @param key
     * @return string
     */
    public String getString(final String key) {
        try {
            String result = configGroup.get(key);
            logger.info("{}对应的结果为：{}", key, result);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getString(String key, String defaultVal) {
        try {
            String result = configGroup.get(key);
            logger.info("{}对应的结果为：{}", key, result);
            return StringUtils.isBlank(result) ? defaultVal : result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return defaultVal;
    }
}

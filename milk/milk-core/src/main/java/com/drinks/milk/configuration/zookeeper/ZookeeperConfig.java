package com.drinks.milk.configuration.zookeeper;

import com.dangdang.config.service.zookeeper.ZookeeperConfigGroup;
import com.dangdang.config.service.zookeeper.ZookeeperConfigProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ZookeeperConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperConfig.class);

    /**
     * ${config.server.zk}值在application.yml里面读取
     *
     */
    @Value("${config.server.zk}")
    private String connectStr;
    @Value("${config.server.project}")
    private String rootNode;
    @Value("${config.server.version}")
    private String version;
    @Value("${config.server.group}")
    private String group;

    /**
     * 初始化ZookeeperConfigProfile
     *
     * @return ZookeeperConfigProfile
     */
    @Bean
    public ZookeeperConfigProfile zookeeperConfigProfile() {
        LOGGER.info("zk连接地址：{}", connectStr);
        return new ZookeeperConfigProfile(connectStr, rootNode, version);
    }

    /**
     * 初始化ZookeeperConfigGroup
     *
     * @param zookeeperConfigProfile
     * @return ZookeeperConfigGroup
     */
    @Bean
    public ZookeeperConfigGroup zookeeperConfigGroup(final ZookeeperConfigProfile zookeeperConfigProfile) {
        return new ZookeeperConfigGroup(zookeeperConfigProfile, group);
    }

}

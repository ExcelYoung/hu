package com.drinks.milk.configuration.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusProperties;
import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.configuration.datasource.config.DataSourceContextHolder;
import com.drinks.milk.configuration.datasource.config.MilkAbstractRoutingDataSource;
import com.drinks.milk.constant.CommonConstant;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>Title: DatasourceConfig</p>
 * 数据源初始化。
 *
 * @author hupenglong
 * @date 2020-06-22 15:51
 */
@Configuration
public class DatasourceConfig {

    @Autowired
    private ConfigService configService;
    @Autowired
    MybatisPlusProperties mybatisPlusProperties;


    /**
     * 初始化DataSourceProperties
     *
     * @return DataSourceProperties
     */
    @Bean
    @Primary
    public DataSourceProperties getDataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setType(DruidDataSource.class);
        return properties;
    }

    /**
     * 初始化DruidDataSource
     *
     * @return DruidDataSource
     */
//    @Bean
/*    public DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(configService.getString(ChaosConstant.Mysql.DATASOURCE_URL));
        dataSource.setUsername(configService.getString(ChaosConstant.Mysql.DATASOURCE_USERNAME));
        dataSource.setPassword(configService.getString(ChaosConstant.Mysql.DATASOURCE_PASSWORD));
        dataSource.setDriverClassName(configService.getString(ChaosConstant.Mysql.DATASOURCE_DRIVER_CLASS_NAME));
        dataSource.setMaxActive(Integer.valueOf(configService.getString(ChaosConstant.Mysql.DATASOURCE_MAXACTIVE)));
        dataSource.setInitialSize(Integer.valueOf(configService.getString(ChaosConstant.Mysql.DATASOURCE_INITIALSIZE)));
        dataSource.setMinIdle(Integer.valueOf(configService.getString(ChaosConstant.Mysql.DATASOURCE_MINIDLE)));
        return dataSource;
    }*/
    @Bean(name = "dsChaos")
    @Primary
    public DruidDataSource dsChaos() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(configService.getString(CommonConstant.Mysql.DATASOURCE_URL));
        dataSource.setUsername(configService.getString(CommonConstant.Mysql.DATASOURCE_USERNAME));
        dataSource.setPassword(configService.getString(CommonConstant.Mysql.DATASOURCE_PASSWORD));
        dataSource.setDriverClassName(configService.getString(CommonConstant.Mysql.DATASOURCE_DRIVER_CLASS_NAME));
        return dataSource;
    }

    @Bean(name = "dsEva")
    public DruidDataSource dsEva() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(configService.getString(CommonConstant.Mysql.Eva.DATASOURCE_URL));
        dataSource.setUsername(configService.getString(CommonConstant.Mysql.Eva.DATASOURCE_USERNAME));
        dataSource.setPassword(configService.getString(CommonConstant.Mysql.Eva.DATASOURCE_PASSWORD));
        return dataSource;
    }

    @Bean
    public AbstractRoutingDataSource routingDataSource() {
        MilkAbstractRoutingDataSource proxy = new MilkAbstractRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceContextHolder.DSCHAOS, dsChaos());
        targetDataSources.put(DataSourceContextHolder.DSEVA, dsEva());
        proxy.setDefaultTargetDataSource(dsChaos());
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(routingDataSource());
        bean.setGlobalConfig(globalConfiguration());
        bean.setConfiguration(mybatisConfiguration());
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(routingDataSource());
    }

    @Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        globalConfiguration.setIdType(0);
        globalConfiguration.setFieldStrategy(2);
        globalConfiguration.setRefresh(true);
        globalConfiguration.setDbColumnUnderline(true);
        globalConfiguration.setLogicDeleteValue("0");
        globalConfiguration.setLogicNotDeleteValue("1");
        globalConfiguration.setDbType("mysql");
        return globalConfiguration;
    }

    @Bean
    public MybatisConfiguration mybatisConfiguration() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        mybatisConfiguration.setCacheEnabled(false);
        mybatisPlusProperties.setTypeAliasesPackage("com.drinks.milk.entity");
        mybatisPlusProperties.setMapperLocations(new String[]{"classpath:/com/drinks/milk/mapper/*Mapper.xml"});
        return mybatisConfiguration;
    }

}

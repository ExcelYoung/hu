package com.drinks.milk.configuration.mybatisplus;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: MybatisPlusConfig</p>
 *
 * @author hupenglong
 * @date 2020-09-01 15:44
 */
@Configuration
//@Component
//@MapperScan(basePackages = {"com.insuranceman.chaos.mapper.**"})
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        return paginationInterceptor;
    }
}

package com.drinks.milk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(value = {"com.drinks.milk.mapper.*"})


public class MilkApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MilkApplication.class);
        application.run(args);
    }

}

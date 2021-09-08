package com.drinks.milk.provider;

import com.drinks.milk.api.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class DemoServiceProvider implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + ", " + new Date();
    }

}
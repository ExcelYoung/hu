package com.drinks.milk.api.controller;

import com.drinks.milk.api.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoServiceController {

    @Autowired
    private DemoService demoService;
    @RequestMapping("/milk/demoservice")
    public void getdemoService(@RequestParam("name")String name){
        System.err.println(demoService.sayHello(name));
    }

}

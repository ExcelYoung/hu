package com.drinks.milk.controller;

import com.drinks.milk.configuration.ConfigService;
import com.drinks.milk.entity.MilkType;
import com.drinks.milk.mapper.MilkTypeMapper;
import com.drinks.milk.mongo.dao.insure.ChaosInsureOrderDao;
import com.drinks.milk.mongo.entity.insure.ChaosInsureOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ConfigService configService;
    @Autowired
    private ChaosInsureOrderDao chaosInsureOrderDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private MilkTypeMapper milkTypeMapper;


    //zkui配置测试
    @ApiOperation(value = "zkui配置测试")
    @PostMapping("/zkui")
    public String getNodeValue(@RequestParam("key") String key) {
        String node = configService.getString(key);
        return node;
    }

    //mogo查询测试
    @ApiOperation(value = "mogo查询测试")
    @PostMapping("/mogo")
    public ChaosInsureOrder findById(@RequestParam("id") String id) {
       return chaosInsureOrderDao.findById(id);
    }

    //Redis测试
    @ApiOperation(value = "Redis测试")
    @PostMapping("/redis") //@Param("setStr") String setStr接收不到数据，必须改成@RequestParam
    public String setStr(@RequestParam("setStr") String setStr) {
        //set进入redis
        stringRedisTemplate.opsForValue().set(setStr,setStr);
        //get到set进去的值
        return stringRedisTemplate.opsForValue().get(setStr);
    }
}


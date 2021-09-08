package com.drinks.milk.configuration.rocketmq;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.drinks.milk.mq.notice.MqMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ConsumerClient {

    @Autowired
    private MqConfig mqConfig;

    //普通消息监听器，Consumer注册消息监听器来订阅消息. 
    @Autowired
    private MqMessageListener messageListener;
    


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqPropertie();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        consumerBean.setProperties(properties);
        //订阅消息
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();
        //订阅普通消息
        Subscription subscription = new Subscription();
        subscription.setTopic(mqConfig.getTopic());
        subscription.setExpression(mqConfig.getTag());
        //subscriptionTable.put(subscription, messageListener);

        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }

}
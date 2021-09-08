package com.drinks.milk.configuration.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DelayQueueTask {

    public static final String DELAY_QUEUE_NAME = "delay:queue:customer_delay_queue";
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ApplicationContext context;
    @Resource
    private RBlockingQueue blockingFairQueue;
    @Resource
    private RDelayedQueue<DelayMessage> delayedQueue;

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 添加任务进入队列中
     * @param delayMessage  消息内容
     * @param num  延时时长
     * @param timeUnit 延时时长单位
     */
    public void offerTask(DelayMessage delayMessage,Long num,TimeUnit timeUnit){
        log.info("消息入队："+ JSONObject.fromObject(delayMessage)+",时间："+sdf.format(System.currentTimeMillis()));
        delayedQueue.offer(delayMessage,num, timeUnit);
    }

    @PostConstruct
    public void startJobTimer() {
        new Thread(() -> {
            DelayMessage delayMessage=null;
            while (!redissonClient.isShutdown()) {
                try {
                    delayMessage =(DelayMessage)blockingFairQueue.take();
                    log.info("监听到的消息："+JSONObject.fromObject(delayMessage) +",时间：" + sdf.format(System.currentTimeMillis()));
                    executorService.execute(new ExecutorTask(context, delayMessage));
                } catch (Exception e) {
                    if (null==executorService||null==delayMessage) {
                        continue;
                    }
                    log.info("消费失败，重回队列:"+JSONObject.fromObject(delayMessage) +",时间：" + sdf.format(System.currentTimeMillis()));
                    backOff(delayMessage);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void backOff(DelayMessage delayMessage){
        if(delayMessage==null){
            return ;
        }
        delayedQueue.offer(delayMessage,10, TimeUnit.SECONDS);
    }


    class ExecutorTask implements Runnable {

        private ApplicationContext context;

        private DelayMessage delayMessage;

        public ExecutorTask(ApplicationContext context, DelayMessage delayMessage) {
            this.context = context;
            this.delayMessage = delayMessage;
        }

        @Override
        public void run() {
            if(delayMessage.getExecuteClass()==null){
                return ;
            }
            DelayExecutor executor = (DelayExecutor) context.getBean(delayMessage.getExecuteClass());
            executor.execute(delayMessage);
        }
    }
}
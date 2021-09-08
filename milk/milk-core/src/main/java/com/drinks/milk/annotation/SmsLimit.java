package com.drinks.milk.annotation;

import com.drinks.milk.enums.VerifCodeType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 短信数量限制注解
 * @Originalauthor: Guo kaixuan
 * @Author：yh
 * @date: 2021/2/19
 * @time: 15:49
 */


@Target(ElementType.METHOD)  //作用在方法上
@Retention(RetentionPolicy.RUNTIME) //在运行的时候是有效的
@Documented //通过给Annotation注解加上@Documented标签，能使该Annotation标签出现在javadoc
public @interface SmsLimit {
    /**
     * 限制条数
     * @return
     */
    int limit() default 10;
    /**
     * 限制作用范围时间
     * @return
     */
    long timeValue() default 1;
    /**
     * 限制作用范围时间单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.DAYS;
    /**
     * 作用范围
     * @return
     */
    VerifCodeType[] scope() default {VerifCodeType.REGISTER, VerifCodeType.COMMON};
}

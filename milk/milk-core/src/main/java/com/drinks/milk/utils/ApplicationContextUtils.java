package com.drinks.milk.utils;

import org.springframework.context.ApplicationContext;

/**
 * <p>Title: ApplicationContextUtils</p>
 *
 * @author hupenglong
 * @date 2020-06-22 17:15
 */
public class ApplicationContextUtils {
    private static ApplicationContext context;

    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> t) {
        return context.getBean(t);
    }


    public static <T> T getBean(String beanName,Class<T> t) {
        return context.getBean(beanName,t);
    }
}

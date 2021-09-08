package com.drinks.milk.configuration.datasource.aspect;

import com.drinks.milk.configuration.datasource.annotation.TargetDataSource;
import com.drinks.milk.configuration.datasource.config.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * <p>Title: DataSourceAdvice</p>
 *
 */
@Aspect
@Component
public class DataSourceAdvice implements Ordered {

    @Around("@annotation(targetDataSource)")
    public Object setRead(ProceedingJoinPoint joinPoint, TargetDataSource targetDataSource) throws Throwable {
        try {
            String dbid = targetDataSource.name();
            if(DataSourceContextHolder.DSEVA.equals(dbid)){
                DataSourceContextHolder.setDbType(DataSourceContextHolder.DSEVA);
            }
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clearDbType();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

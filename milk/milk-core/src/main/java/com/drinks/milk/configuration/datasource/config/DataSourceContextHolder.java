package com.drinks.milk.configuration.datasource.config;

/**
 * <p>Title: DataSourceContextHolder</p>
 *
 * @author hupenglong
 * @date 2020-06-29 11:59
 */
public class DataSourceContextHolder {
    public static final String DSCHAOS = "dsChaos";
    public static final String DSEVA = "dsEva";
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        if (dbType == null) {
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return contextHolder.get() == null ? DSCHAOS : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}

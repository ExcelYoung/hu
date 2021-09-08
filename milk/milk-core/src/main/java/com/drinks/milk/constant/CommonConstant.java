package com.drinks.milk.constant;

/**
 * <p>Title: CommonConstant</p>
 *
 * @author yaobw
 * @date 2020-06-29
 */
public class CommonConstant {

    public static final int CONSUMER_TIMEOUT = 60000;


    public static final class Dubbo {
        public Dubbo() {
        }

        public static final String REGISTRY_ADDRESS = "dubbo.registry.address";
        public static final String REGISTRY_ID = "dubbo.registry.id";
        public static final String PROTOCOL_NAME = "dubbo.protocol.name";
        public static final String PROTOCOL_PORT = "dubbo.protocol.port";
        public static final String APPLICATION_NAME = "dubbo.application.name";
        public static final String CONSUMER_TIMEOUT = "dubbo.consumer.timeout";
        public static final String REGISTRY_GROUP = "dubbo.registry.group";
        public static final String APPLICATION_ENV = "dubbo.application.env";
        public static final String DISPATCHER = "dubbo.protocol.dispatcher";
        public static final String THREADS = "dubbo.protocol.threads";
        public static final String THREAD_POOL = "dubbo.protocol.threadspool";

    }

    public static final class Redis {
        private Redis() {
        }

        public static final String REDIS_HOST = "spring.redis.host";
        public static final String REDIS_PASSWORD = "spring.redis.password";
        public static final String REDIS_PORT = "spring.redis.port";
        public static final String REDIS_TIMEOUT = "spring.redis.timeout";
        public static final String REDIS_DATASOURCE = "spring.redis.datasource";
        public static final String REDIS_JEDISPOOL_MAXACTIVE = "spring.redis.jedis.pool.max-active";
        public static final String REDIS_JEDISPOOL_MAXWAIT = "spring.redis.jedis.pool.max-wait";
        public static final String REDIS_JEDISPOOL_MAXIDLE = "spring.redis.jedis.pool.max-idle";
        public static final String REDIS_JEDISPOOL_MINIDLE = "spring.redis.jedis.pool.min-idle";

    }

    public static final class Mysql {
        public Mysql() {
        }

        private static final String PREFIX = "spring.datasource.";
        public static final String DATASOURCE_TYPE = PREFIX + "type";

        public static final String DATASOURCE_URL = PREFIX + "druid.url";
        public static final String DATASOURCE_USERNAME = PREFIX + "druid.username";
        public static final String DATASOURCE_PASSWORD = PREFIX + "druid.password";
        public static final String DATASOURCE_DRIVER_CLASS_NAME = PREFIX + "druid.driver-class-name";
        public static final String DATASOURCE_MAXACTIVE = PREFIX + "druid.maxActive";
        public static final String DATASOURCE_INITIALSIZE = PREFIX + "druid.initialSize";
        public static final String DATASOURCE_MINIDLE = PREFIX + "druid.minIdle";

        //todo 待配置
        public static final class Eva {
            public static final String DATASOURCE_URL = PREFIX + "druid.eva.url";
            public static final String DATASOURCE_USERNAME = PREFIX + "druid.eva.username";
            public static final String DATASOURCE_PASSWORD = PREFIX + "druid.eva.password";
        }

    }


    public static final class Mongo {
        public Mongo() {

        }
        public static final String DATA_MONGO_URI = "spring.data.mongo.uri";
    }

    public static final class RabbitMq {
        public RabbitMq() {

        }

        public static final String HOST = "spring.rabbitmq.host";
        public static final String PORT = "spring.rabbitmq.port";
        public static final String USERNAME = "spring.rabbitmq.username";
        public static final String PASSWORD = "spring.rabbitmq.password";
        public static final String VIRTUAL_HOST = "spring.rabbitmq.virtualhost";
        public static final String LISTENER_SIMPLE_CONCURRENCY = "spring.rabbitmq.listener.simple.concurrency";
        public static final String LISTENER_SIMPLE_MAXCONCURRENCY = "spring.rabbitmq.listener.simple.maxconcurrency";
    }

    public static final class Pantheon {
        public Pantheon() {
        }

        public static final String THIRD_LOGIN = "pantheon.config.thirdlogin";
        public static final String THIRD_LOGIN_TRUE = "true";
        public static final int RESUBMITDATADEFAULT = 1000;
        public static final String NO_TOKEN_MAPPING = "pantheon.token.notokenMapping";
        public static final String REFRESH_TOKEN_MAPPING = "pantheon.token.activeMapping";
        public static final String TOKEN_EXPIRE = "pantheon.token.expire";
        public static final String TOKEN_INTERCEPTOR_PATH = "pantheon.token.pathPatterns";
    }

    public static final class WechatAuth {
        public WechatAuth() {
        }

        public static final String WEIXIN_BXXAPPID = "pantheon.config.wechatauth.bxxAppid";
        public static final String WEIXIN_BXXAPPSECRET = "pantheon.config.wechatauth.bxxAppSecret";
        public static final String WEIXIN_TOKEN_REQUESTURL = "pantheon.config.wechatauth.token.requestUrl";
        public static final String WEIXIN_TICKET_REQUESTURL = "pantheon.config.wechatauth.ticket.requestUrl";
    }

    public static final class QiniuAuth {
        public QiniuAuth() {

        }
        public static final String QINIU_ACCESSKEY = "qiniu.accessKey";
        public static final String QINIU_SECRETKEY = "qiniu.secretKey";
        public static final String QINIU_BUCKET = "qiniu.bucket";
    }

    public static final class Rpc {
        private Rpc() {
        }

        public static final String USER_ID = "userId";
    }

}

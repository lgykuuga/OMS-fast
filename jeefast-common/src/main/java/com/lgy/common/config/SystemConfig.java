package com.lgy.common.config;

import com.lgy.common.constant.Constants;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description TODO
 * @Author LGy
 * @Date 2020/4/20 18:06
 **/
public class SystemConfig {

    /**
     * 任务调度xxl_job
     */
    @Value("${lgy.xxl}")
    private static String xxl;

    /**
     * redis
     */
    @Value("${lgy.redis}")
    private static String redis;

    /**
     * mongoDB
     */
    @Value("${lgy.mongoDB}")
    private static String mongoDB;

    /**
     * rabbitMQ
     */
    @Value("${lgy.rabbitMQ}")
    private static String rabbitMQ;

    /**
     * elasticSearch
     */
    @Value("${lgy.elasticSearch}")
    private static String elasticSearch;


    public static boolean isOpenXxlJob() {
        return Constants.ON.equals(xxl);
    }

    public static boolean isOpenRedis() {
        return Constants.ON.equals(redis);
    }

    public static boolean isOpenMongoDb() {
        return Constants.ON.equals(mongoDB);
    }

    public static boolean isOpenRabbitMq() {
        return Constants.ON.equals(rabbitMQ);
    }

    public static boolean isOpenElasticSearch() {
        return Constants.ON.equals(elasticSearch);
    }

}

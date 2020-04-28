package com.lgy.common.config;

import com.lgy.common.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author LGy
 * @Date 2020/4/20 18:06
 **/
@Configuration
public class SystemConfig {

    /**
     * 任务调度xxl_job
     */
    @Value("${lgy.xxl}")
    private String xxl;

    /**
     * redis
     */
    @Value("${lgy.redis}")
    private String redis;

    /**
     * mongoDB
     */
    @Value("${lgy.mongoDB}")
    private String mongoDB;

    /**
     * rabbitMQ
     */
    @Value("${lgy.rabbitMQ}")
    private String rabbitMQ;

    /**
     * elasticSearch
     */
    @Value("${lgy.elasticSearch}")
    private String elasticSearch;

    /**
     * canal
     */
    @Value("${lgy.canal}")
    private String canal;

    public boolean isOpenXxl() {
        return Constants.ON.equals(xxl);
    }
    public boolean isOpenRedis() {
        return Constants.ON.equals(redis);
    }
    public boolean isOpenMongoDB() {
        return Constants.ON.equals(mongoDB);
    }
    public boolean isOpenRabbitMQ() {
        return Constants.ON.equals(rabbitMQ);
    }
    public boolean isOpenElasticSearch() {
        return Constants.ON.equals(elasticSearch);
    }
    public boolean isOpenCanal() {
        return Constants.ON.equals(canal);
    }

}

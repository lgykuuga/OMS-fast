package com.lgy.web.controller.oms.test;

import com.lgy.common.config.Global;
import com.lgy.common.config.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TestController
 * @Author LGy
 * @Date 2020/1/16 17:56
 **/
@RestController
@RequestMapping("/test/getParams")
@Api("测试接口")
public class TestParamsController {

    public Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/getStartParams")
    @ApiOperation(value = "获取参数", httpMethod = "POST")
    public String getParams() {

        StringBuilder sb = new StringBuilder();

        sb.append("xxl_job启用状态:").append(SystemConfig.isOpenXxlJob());
        sb.append(";redis启用状态:").append(SystemConfig.isOpenRedis());
        sb.append(";mongoDB启用状态:").append(SystemConfig.isOpenMongoDb());
        sb.append(";rabbitMQ启用状态:").append(SystemConfig.isOpenRabbitMq());
        sb.append(";elasticSearch启用状态:").append(SystemConfig.isOpenElasticSearch());

        log.info(sb.toString());
        return sb.toString();
    }
}

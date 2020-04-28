package com.lgy.web.controller.oms.test;

import com.lgy.common.config.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("参数接口")
public class TestParamsController {

    public Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    SystemConfig systemConfig;

    @PostMapping("/getStartParams")
    @ApiOperation(value = "获取参数", httpMethod = "POST")
    public String getParams() {

        StringBuilder sb = new StringBuilder();

        sb.append(";xxl_job启用状态:").append(systemConfig.isOpenXxl()).append("\r\n");
        sb.append(";redis启用状态:").append(systemConfig.isOpenRedis()).append("\r\n");
        sb.append(";mongoDB启用状态:").append(systemConfig.isOpenMongoDB()).append("\r\n");
        sb.append(";rabbitMQ启用状态:").append(systemConfig.isOpenRabbitMQ()).append("\r\n");
        sb.append(";elasticSearch启用状态:").append(systemConfig.isOpenElasticSearch()).append("\r\n");
        sb.append(";elasticSearch启用状态:").append(systemConfig.isOpenCanal()).append("\r\n");
        log.info(sb.toString());
        return sb.toString();
    }
}

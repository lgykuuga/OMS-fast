package com.lgy.web.controller.oms.test;

import com.lgy.es.EsSearchEngine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description ElasticSearchController
 * @Author LGy
 * @Date 2020/4/28 17:56
 **/
@RestController
@RequestMapping("/test/elasticSearch")
@Api("elasticSearch接口")
public class ElasticSearchController {

    public Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    EsSearchEngine esSearchEngine;

    @PostMapping("/getData")
    @ApiOperation(value = "获取参数", httpMethod = "POST")
    public String getData(String id) {

        StringBuilder sb = new StringBuilder();


        log.info(sb.toString());
        return sb.toString();
    }

    @PostMapping("/updateData")
    @ApiOperation(value = "更新", httpMethod = "POST")
    public String updateData(String id) {

        StringBuilder sb = new StringBuilder();

        log.info(sb.toString());
        return sb.toString();
    }

    @PostMapping("/deleteData")
    @ApiOperation(value = "删除", httpMethod = "POST")
    public String deleteData(String id) {

        StringBuilder sb = new StringBuilder();

        log.info(sb.toString());
        return sb.toString();
    }



}

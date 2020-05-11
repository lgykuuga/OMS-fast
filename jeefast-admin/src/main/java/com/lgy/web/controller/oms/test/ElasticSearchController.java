package com.lgy.web.controller.oms.test;

import com.lgy.biz.order.OrderEsSearchEngine;
import com.lgy.common.core.domain.CommonResponse;
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
    OrderEsSearchEngine orderEsSearchEngine;

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
        CommonResponse<String> response = orderEsSearchEngine.deleteDataById(id);
        log.info(response.getMsg());
        return response.getMsg();
    }

    @PostMapping("/createIndex")
    @ApiOperation(value = "创建索引", httpMethod = "POST")
    public CommonResponse<String> createIndex(String index) {
        return orderEsSearchEngine.createIndex(index);
    }

    @PostMapping("/deleteIndex")
    @ApiOperation(value = "删除索引", httpMethod = "POST")
    public CommonResponse<String> deleteIndex(String index) {
        return orderEsSearchEngine.deleteIndex(index);
    }

}

package com.lgy.web.controller.oms.test;

import com.lgy.base.domain.Commodity;
import com.lgy.base.service.ICommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description RedisController
 * @Author LGy
 * @Date 2020/1/16 17:56
 **/
@RestController
@RequestMapping("/test/redis")
@Api("redis测试接口")
public class RedisController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ICommodityService commodityService;


    @PostMapping("/getCommodity")
    @ApiOperation(value = "获取商品", httpMethod = "POST")
    public String getCommodity(String key) {

        Commodity one = commodityService.findOne(key);
        System.out.println(one);
        return "";
    }
}

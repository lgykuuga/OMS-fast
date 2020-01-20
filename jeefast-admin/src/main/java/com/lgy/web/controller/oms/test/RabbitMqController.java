package com.lgy.web.controller.oms.test;


import com.lgy.common.constant.Constants;
import com.lgy.oms.mq.convert.ConvertMqProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description RabbitMq接口
 * @Author LGy
 * @Date 2019/12/31 9:59
 **/
@RestController
@RequestMapping("/test/rabbitmq")
@Api("rabbitmq测试接口")
public class RabbitMqController {

    @Autowired
    ConvertMqProducer convertMqProducer;

    /**
     * 发送MQ
     *
     * @return
     */
    @CrossOrigin
    @PostMapping("/send")
    @ApiOperation(value = "发送MQ", httpMethod = "POST")
    public void send(String tids) {
        String[] tidz = tids.split(Constants.COMMA);
        for (String tid : tidz) {
            convertMqProducer.send(tid);
        }
    }

}

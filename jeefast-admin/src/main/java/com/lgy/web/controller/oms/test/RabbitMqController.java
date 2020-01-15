package com.lgy.web.controller.oms.test;


import com.lgy.oms.domain.order.OrderBuyerInfo;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mq.convert.ConvertProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    ConvertProducer convertProducer;

    /**
     * 发送MQ
     *
     * @return
     */
    @CrossOrigin
    @PostMapping("/send")
    @ApiOperation(value = "发送MQ", httpMethod = "POST")
    public void send() {
        OrderMain orderMain = new OrderMain();
        orderMain.setOrderId("123");
        OrderBuyerInfo buyerInfo = new OrderBuyerInfo();
        buyerInfo.setOrderId("123");
        buyerInfo.setBuyerId("hahahhaha");
        orderMain.setOrderBuyerinfo(buyerInfo);
        convertProducer.send(orderMain);
    }

}

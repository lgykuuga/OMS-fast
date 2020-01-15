package com.lgy.oms.mq.convert;

import com.lgy.common.config.RabbitMqConfig;
import com.lgy.framework.util.MessageHelper;
import com.lgy.oms.domain.order.OrderMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 转单MQ生产者
 * @Author LGy
 * @Date 2020/1/14 18:08
 **/
@Component
public class ConvertProducer {

    private static Logger logger = LoggerFactory.getLogger(ConvertProducer.class);

    @Resource
    AmqpTemplate rabbitTemplate;

    public void send(OrderMain orderMain) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.CONVERT_QUEUE_NAME, MessageHelper.objToMsg(orderMain));
        logger.debug("消息队列rabbitMQ转单处理单据[{}]", orderMain.getOrderId());
    }

}

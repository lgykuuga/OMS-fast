package com.lgy.oms.mq.audit;

import com.lgy.common.config.RabbitMqConfig;
import com.lgy.framework.util.MessageHelper;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 审单MQ生产者
 * @Author LGy
 * @Dte 2020/1/14 18:08
 **/
@Component
public class AuditMqProducer {

    private static Logger logger = LoggerFactory.getLogger(AuditMqProducer.class);

    @Autowired
    AmqpTemplate rabbitTemplate;

    public void send(OrderMain orderMain, AuditParamDTO param) {

        AuditOrderMessage message = new AuditOrderMessage();
        message.setOrderMain(orderMain);
        message.setParam(param);
        message.setSysUser(ShiroUtils.getSysUser());

        rabbitTemplate.convertAndSend(RabbitMqConfig.AUDIT_QUEUE_NAME, MessageHelper.objToMsg(message));
        logger.debug("消息队列rabbitMQ审单处理单据[{}]", orderMain.getOrderId());
    }

}

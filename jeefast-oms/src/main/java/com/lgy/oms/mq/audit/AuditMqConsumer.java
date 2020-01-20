package com.lgy.oms.mq.audit;

import com.lgy.common.config.RabbitMqConfig;
import com.lgy.common.constant.Method;
import com.lgy.common.constant.ResponseCode;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.framework.util.MessageHelper;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.IAuditOrderService;
import com.lgy.oms.biz.ITradeConvertService;
import com.lgy.oms.domain.MqErrorMessage;
import com.lgy.oms.domain.dto.TradeParamDTO;
import com.lgy.oms.service.IMqErrorMessageService;
import com.lgy.system.domain.SysUser;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Description 审单 RabbitMQ消费者
 * @Author LGy
 * @Date 2020/1/14 17:51
 **/
@Component
@ConditionalOnProperty(name = "lgy.rabbitMQ", havingValue = "0", matchIfMissing = true)
public class AuditMqConsumer {

    private static Logger logger = LoggerFactory.getLogger(AuditMqConsumer.class);

    @Autowired
    IAuditOrderService auditOrderService;

    @Autowired
    IMqErrorMessageService mqErrorMessageService;

    /**
     * 审单队列
     */
    private final String queue = RabbitMqConfig.AUDIT_QUEUE_NAME;

    /**
     * 执行方法--消息回调方法
     */
    @RabbitListener(queues = queue, containerFactory = Method.MULTI)
    public void onMessage(Message message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                          Channel channel) throws IOException {

        try {
            AuditOrderMessage auditOrderMessage = MessageHelper.msgToObj(message, AuditOrderMessage.class);
            logger.debug("审单消费者MQ接收订单[{}]", auditOrderMessage.getOrderMain().getOrderId());


            ShiroUtils.setUserThreadLocal(auditOrderMessage.getSysUser());

            //执行业务代码
            CommonResponse<String> execute = execute(auditOrderMessage);
            //执行异常,记录MQ
            if (ResponseCode.RESEND.equals(execute.getCode())) {
                onError(execute.getMsg(), new String(message.getBody()));
            }

            ShiroUtils.removeUserThreadLocal();

        } catch (Exception e) {
            logger.error("审单消费异常:", e);

        } finally {
            // 消息确认,否则会导致消息死循环
            channel.basicAck(deliveryTag, true);
        }
    }

    private CommonResponse<String> execute(AuditOrderMessage message) {
        return auditOrderService.start(message.getOrderMain(), message.getParam());
    }

    /**
     * 记录异常信息
     */
    private void onError(String msg, String content) {
        MqErrorMessage mqErrorMessage = new MqErrorMessage();
        mqErrorMessage.setQueue(queue);
        mqErrorMessage.setError(msg);
        mqErrorMessage.setContent(content);
        mqErrorMessage.setCreateTime(new Date());
        mqErrorMessageService.save(mqErrorMessage);
    }

}

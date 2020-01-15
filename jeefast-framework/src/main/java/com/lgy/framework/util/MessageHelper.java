package com.lgy.framework.util;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;

/**
 * @Description rabbitMQ MessageHelper
 * @Author LGy
 * @Date 2020/1/15
 */
public class MessageHelper {

    public static Message objToMsg(Object obj) {
        if (null == obj) {
            return null;
        }

        Message message = MessageBuilder.withBody(JSON.toJSONString(obj).getBytes()).build();
        // 消息持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        //登录用户,用于线程切换
        message.getMessageProperties().setUserId(ShiroUtils.getUserId().toString());
        return message;
    }

    public static <T> T msgToObj(Message message, Class<T> clazz) {
        if (null == message || null == clazz) {
            return null;
        }

        String str = new String(message.getBody());
        return JSON.parseObject(str, clazz);
    }

}

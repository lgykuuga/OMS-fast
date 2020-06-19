package com.lgy.oms.factory;

import com.lgy.oms.domain.order.OrderInterceptInfo;

/**
 * @Description 订单拦截Factory
 * @Author LGy
 * @Date 2020/6/19 14:05
 **/
public class OrderInterceptFactory {

    public static OrderInterceptInfo create(String orderId, Integer type, String content) {
        OrderInterceptInfo orderInterceptInfo = new OrderInterceptInfo();
        orderInterceptInfo.setOrderId(orderId);
        orderInterceptInfo.setType(type);
        orderInterceptInfo.setContent(content);
        return orderInterceptInfo;
    }
}

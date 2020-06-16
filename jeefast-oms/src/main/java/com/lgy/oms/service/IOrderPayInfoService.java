package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.order.OrderPayInfo;

/**
 * 订单支付信息 服务层
 *
 * @author lgy
 * @date 2019-12-13
 */
public interface IOrderPayInfoService extends IService<OrderPayInfo> {

    /**
     * getByOrderId
     *
     * @param orderId
     * @return
     */
    OrderPayInfo getByOrderId(String orderId);
}
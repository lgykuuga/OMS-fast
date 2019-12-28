package com.lgy.oms.biz;

import com.lgy.oms.domain.order.OrderMain;

/**
 * @Description 创建主订单信息
 * @Author LGy
 * @Date 2019/12/10
 */
public interface ICreateOrderMainService {

    /**
     * 保存订单
     *
     * @param orderMain 订单主体
     * @return
     */
    OrderMain saveOrder(OrderMain orderMain);

}

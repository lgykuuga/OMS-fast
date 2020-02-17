package com.lgy.oms.biz;

import com.lgy.oms.domain.order.OrderMain;

/**
 * @Description 更新订单状态标识
 * @Author LGy
 * @Date 2020/2/17
 */
public interface IUpdateOrderFlagService {

    /**
     * 审核成功更新订单
     *
     * @param orderMain 订单主信息
     * @return
     */
    boolean auditUpdateOrder(OrderMain orderMain);

    /**
     * 配货成功更新订单
     *
     * @param orderMain 订单主信息
     * @return
     */
    boolean distributionUpdateOrder(OrderMain orderMain);

}

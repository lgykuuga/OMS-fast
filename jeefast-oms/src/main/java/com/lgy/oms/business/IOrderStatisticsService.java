package com.lgy.oms.business;


import com.lgy.oms.domain.order.OrderMain;

/**
 * @Description 订单整理计算统计功能开发
 * @Author LGy
 * @Date 2019/12/12
 */
public interface IOrderStatisticsService {

    /**
     * 订单统计整理
     *
     * @param orderMain 订单主信息
     */
    void orderStatisticsMethod(OrderMain orderMain);

}

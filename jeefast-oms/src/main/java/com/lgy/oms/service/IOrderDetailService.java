package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.order.OrderDetail;

/**
 * 订单明细信息 服务层
 *
 * @author lgy
 * @date 2019-12-13
 */
public interface IOrderDetailService extends IService<OrderDetail> {

    /**
     * 校验组合商品下的组合商品明细是否存在
     *
     * @param orderDetail 订单明细行
     * @return 不为空, 返回true
     */
    boolean checkOrderCombDetailExist(OrderDetail orderDetail);

}
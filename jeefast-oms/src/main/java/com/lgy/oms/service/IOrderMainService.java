package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.order.OrderMain;

import java.util.List;

/**
 * 订单主信息 服务层
 *
 * @author lgy
 * @date 2019-11-25
 */
public interface IOrderMainService extends IService<OrderMain> {

    /**
     * 根据来源单号获取订单号
     *
     * @param sourceId 来源单号
     * @param status   true:有效订单/false:全部订单
     * @return
     */
    List<String> getOrderIdBySourceId(String sourceId, Boolean status);

}
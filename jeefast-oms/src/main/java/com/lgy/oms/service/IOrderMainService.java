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

    /**
     * 根据订单号获取订单主信息
     *
     * @param orderId 订单号
     * @return
     */
    OrderMain getOrderById(String orderId);

    /**
     * 根据订单号获取订单所有信息
     *
     * @param orderId
     * @return
     */
    OrderMain getOrderFullInfoById(String orderId);

    /**
     * 校验是否存在相同来源单号的有效单据
     *
     * @param orderId  订单号
     * @param sourceId 来源单号
     * @param owner    货主
     * @param status   状态
     * @return
     */
    List<String> checkSameOrderExist(String orderId, String sourceId, String owner, Integer status);

    /**
     * 审核成功更新订单
     *
     * @param orderMain 订单主信息
     * @return
     */
    boolean auditUpdateOrder(OrderMain orderMain);
}
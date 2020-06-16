package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.order.OrderInterceptInfo;

import java.util.List;

/**
 * 订单拦截信息 服务层
 *
 * @author lgy
 * @date 2019-12-13
 */
public interface IOrderInterceptService extends IService<OrderInterceptInfo> {


    /**
     * 新增或更新拦截订单信息
     *
     * @param orderId 订单号
     * @param type    拦截类型
     * @param content 拦截内容
     * @return
     */
    Integer addOrUpdateOrderIntercept(String orderId, Integer type, String content);


    /**
     * 清除订单拦截信息
     *
     * @param orderId 订单号
     */
    void deleteByOrderId(String orderId);

    /**
     * 清除订单拦截信息并更新订单状态
     *
     * @param orderId 订单号
     */
    void deleteAndUpdateStatByOrderId(String orderId);

    /**
     * 批量清除订单拦截信息并更新订单状态
     *
     * @param orderIds 订单号集合
     */
    void deleteAndUpdateStatByOrderIds(List<String> orderIds);

    /**
     * getByOrderId
     *
     * @param orderId 订单号
     * @return
     */
    OrderInterceptInfo getByOrderId(String orderId);
}
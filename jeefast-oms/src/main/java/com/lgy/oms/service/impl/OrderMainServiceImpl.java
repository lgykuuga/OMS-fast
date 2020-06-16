package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.oms.domain.order.*;
import com.lgy.oms.domain.vo.OrderVO;
import com.lgy.oms.mapper.OrderMainMapper;
import com.lgy.oms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 订单审核信息 服务层实现
 *
 * @author lgy
 * @date 2019-11-25
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {


    @Autowired
    OrderMainMapper orderMainMapper;
    /**
     * 订单状态信息
     */
    @Autowired
    IOrderStatusService orderStatusService;
    /**
     * 订单支付信息
     */
    @Autowired
    IOrderPayInfoService orderPayInfoService;
    /**
     * 订单买家信息
     */
    @Autowired
    IOrderBuyerInfoService orderBuyerInfoService;
    /**
     * 订单类型信息
     */
    @Autowired
    IOrderTypeInfoService orderTypeInfoService;
    /**
     * 订单明细信息
     */
    @Autowired
    IOrderDetailService orderDetailService;
    /**
     * 订单拦截信息
     */
    @Autowired
    IOrderInterceptService orderInterceptService;


    @Override
    public List<String> getOrderIdBySourceId(String sourceId, Boolean status) {

        QueryWrapper<OrderStatusInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderStatusInfo::getSourceId, sourceId);
        if (status) {
            queryWrapper.lambda().eq(OrderStatusInfo::getStatus, Constants.VALID);
        }
        queryWrapper.lambda().select(OrderStatusInfo::getOrderId);
        List<OrderStatusInfo> list = orderStatusService.list(queryWrapper);
        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream().map(OrderStatusInfo::getOrderId).collect(Collectors.toList());
    }

    @Override
    public OrderMain getOrderById(String orderId) {
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderMain::getOrderId, orderId);
        return this.getOne(queryWrapper);
    }

    @Override
    public OrderMain getOrderFullInfoById(String orderId) {
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderMain::getOrderId, orderId);
        //订单主信息
        OrderMain orderMain = this.getOne(queryWrapper);

        if (Objects.isNull(orderMain)) {
            return null;
        }
        //订单状态信息
        OrderStatusInfo orderStatusInfo = orderStatusService.getByOrderId(orderMain.getOrderId());
        orderMain.setOrderStatusinfo(orderStatusInfo);
        //订单支付信息
        OrderPayInfo orderPayInfo = orderPayInfoService.getByOrderId(orderMain.getOrderId());
        orderMain.setOrderPayinfo(orderPayInfo);
        //订单买家信息
        OrderBuyerInfo orderBuyerInfo = orderBuyerInfoService.getByOrderId(orderMain.getOrderId());
        orderMain.setOrderBuyerinfo(orderBuyerInfo);
        //订单类型信息
        OrderTypeInfo orderTypeInfo = orderTypeInfoService.getByOrderId(orderMain.getOrderId());
        orderMain.setOrderTypeinfo(orderTypeInfo);
        //订单拦截信息
        OrderInterceptInfo orderInterceptInfo = orderInterceptService.getByOrderId(orderMain.getOrderId());
        orderMain.setOrderInterceptInfo(orderInterceptInfo);
        //订单明细信息
        List<OrderDetail> orderDetailList = orderDetailService.getByOrderId(orderMain.getOrderId());
        orderMain.setOrderDetails(orderDetailList);
        return orderMain;

    }

    @Override
    public List<String> checkSameOrderExist(String orderId, String sourceId, String owner, Integer status) {
        QueryWrapper<OrderStatusInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lambda()
                .eq(OrderStatusInfo::getSourceId, sourceId)
                .eq(OrderStatusInfo::getStatus, Constants.VALID)
                .ne(OrderStatusInfo::getOrderId, orderId)
                .select(OrderStatusInfo::getOrderId);
        List<OrderStatusInfo> list = orderStatusService.list(queryWrapper);
        return list.stream().map(OrderStatusInfo::getOrderId).collect(Collectors.toList());
    }

    @Override
    public List<OrderVO> queryOrderList(OrderVO orderVO) {
        return orderMainMapper.queryOrderList(orderVO);
    }
}
package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.order.*;
import com.lgy.oms.enums.order.OrderFlagEnum;
import com.lgy.oms.mapper.OrderMainMapper;
import com.lgy.oms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.TransactionManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单审核信息 服务层实现
 *
 * @author lgy
 * @date 2019-11-25
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {

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
        queryWrapper.eq("source_id", sourceId);
        if (status) {
            queryWrapper.eq("status", Constants.VALID);
        }
        queryWrapper.select("order_id");
        List<OrderStatusInfo> list = orderStatusService.list(queryWrapper);
        if (list == null) {
            return null;
        }

        List<String> orderIdList = new ArrayList<>(list.size());
        list.forEach(orderStatus -> {
            orderIdList.add(orderStatus.getOrderId());
        });

        return orderIdList;
    }

    @Override
    public OrderMain getOrderById(String orderId) {
        QueryWrapper<OrderMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return this.getOne(queryWrapper);
    }

    @Override
    public OrderMain getOrderFullInfoById(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        //订单主信息
        OrderMain orderMain = this.getOne(queryWrapper);

        if (orderMain == null) {
            return null;
        }
        //订单状态信息
        OrderStatusInfo orderStatusInfo = orderStatusService.getOne(queryWrapper);
        if (orderStatusInfo != null) {
            orderMain.setOrderStatusinfo(orderStatusInfo);
        }
        //订单支付信息
        OrderPayInfo orderPayInfo = orderPayInfoService.getOne(queryWrapper);
        if (orderPayInfo != null) {
            orderMain.setOrderPayinfo(orderPayInfo);
        }
        //订单买家信息
        OrderBuyerInfo orderBuyerInfo = orderBuyerInfoService.getOne(queryWrapper);
        if (orderBuyerInfo != null) {
            orderMain.setOrderBuyerinfo(orderBuyerInfo);
        }
        //订单类型信息
        OrderTypeInfo orderTypeInfo = orderTypeInfoService.getOne(queryWrapper);
        if (orderTypeInfo != null) {
            orderMain.setOrderTypeinfo(orderTypeInfo);
        }
        //订单拦截信息
        OrderInterceptInfo orderInterceptInfo = orderInterceptService.getOne(queryWrapper);
        if (orderInterceptInfo != null) {
            orderMain.setOrderInterceptInfo(orderInterceptInfo);
        }
        //订单明细信息
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        if (StringUtils.isNotEmpty(orderDetailList)) {
            orderMain.setOrderDetails(orderDetailList);
        }

        return orderMain;

    }

    @Override
    public List<String> checkSameOrderExist(String orderId, String sourceId, String owner, Integer status) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", sourceId);
        queryWrapper.eq("status", Constants.VALID);
        queryWrapper.ne("order_id", orderId);
        queryWrapper.select("order_id");
        return orderStatusService.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditUpdateOrder(OrderMain orderMain) {

        boolean flag = false;

        OrderStatusInfo orderStatusinfo = orderMain.getOrderStatusinfo();
        //更新状态为已审核
        orderStatusinfo.setFlag(OrderFlagEnum.AUDIT.getCode());
        //更新条件
        UpdateWrapper<OrderStatusInfo> orderStatusWrapper = new UpdateWrapper<>();
        orderStatusWrapper.eq("order_id", orderMain.getOrderId());
        orderStatusWrapper.eq("status", Constants.VALID);
        orderStatusWrapper.in("flag", OrderFlagEnum.NEW.getCode(), OrderFlagEnum.EDIT.getCode());

        boolean update = orderStatusService.update(orderMain.getOrderStatusinfo(), orderStatusWrapper);

        if (update) {
            //设置放行
            orderMain.setIntercept(Constants.NO);
            //设置解冻
            orderMain.setFrozen(Constants.NO);

            UpdateWrapper<OrderMain> mainUpdateWrapper = new UpdateWrapper<>();
            mainUpdateWrapper.eq("order_id", orderMain.getOrderId());
            flag = this.update(orderMain, mainUpdateWrapper);
        }

        if (!flag) {
            //更新失败,手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return flag;
    }
}
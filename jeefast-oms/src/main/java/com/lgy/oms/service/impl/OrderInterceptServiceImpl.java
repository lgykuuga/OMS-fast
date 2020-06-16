package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mapper.OrderInterceptMapper;
import com.lgy.oms.service.IOrderInterceptService;
import com.lgy.oms.service.IOrderMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单拦截信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderInterceptServiceImpl extends ServiceImpl<OrderInterceptMapper, OrderInterceptInfo> implements IOrderInterceptService {

    @Autowired
    IOrderMainService orderMainService;
    @Autowired
    OrderInterceptMapper orderInterceptMapper;

    @Override
    public Integer addOrUpdateOrderIntercept(String orderId, Integer type, String content) {

        //更新订单主信息状态
        OrderMain orderMain = new OrderMain();
        orderMain.setIntercept(Constants.YES);
        UpdateWrapper<OrderMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(OrderMain::getOrderId, orderId);
        boolean b = orderMainService.update(orderMain, updateWrapper);
        if (b) {
            //新增或更新拦截订单信息
            return orderInterceptMapper.addOrUpdateOrderIntercept(orderId, type, content);
        }
        return 0;
    }

    @Override
    public void deleteByOrderId(String orderId) {
        QueryWrapper<OrderInterceptInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderInterceptInfo::getOrderId, orderId);
        this.remove(queryWrapper);
    }

    @Override
    public void deleteAndUpdateStatByOrderId(String orderId) {
        //删除拦截信息
        deleteByOrderId(orderId);
        //更新订单主信息状态
        OrderMain orderMain = new OrderMain();
        orderMain.setIntercept(Constants.NO);
        UpdateWrapper<OrderMain> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(OrderMain::getOrderId, orderId);
        orderMainService.update(orderMain, updateWrapper);
    }

    @Override
    public void deleteAndUpdateStatByOrderIds(List<String> orderIds) {

        for (String orderId : orderIds) {
            //删除拦截信息
            deleteByOrderId(orderId);
            //更新订单主信息状态
            OrderMain orderMain = new OrderMain();
            orderMain.setIntercept(Constants.NO);
            UpdateWrapper<OrderMain> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().eq(OrderMain::getOrderId, orderId);
            orderMainService.update(orderMain, updateWrapper);
        }

    }

    @Override
    public OrderInterceptInfo getByOrderId(String orderId) {
        QueryWrapper<OrderInterceptInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderInterceptInfo::getOrderId, orderId);
        return this.getOne(queryWrapper);
    }
}
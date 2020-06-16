package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderTypeInfo;
import com.lgy.oms.mapper.OrderTypeInfoMapper;
import com.lgy.oms.service.IOrderTypeInfoService;
import org.springframework.stereotype.Service;

/**
 * 订单类型信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderTypeInfoServiceImpl extends ServiceImpl<OrderTypeInfoMapper, OrderTypeInfo> implements IOrderTypeInfoService {

    @Override
    public OrderTypeInfo getByOrderId(String orderId) {
        QueryWrapper<OrderTypeInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderTypeInfo::getOrderId, orderId);
        return this.getOne(queryWrapper);
    }
}
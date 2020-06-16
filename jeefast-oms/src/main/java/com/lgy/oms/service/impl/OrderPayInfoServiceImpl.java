package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderPayInfo;
import com.lgy.oms.mapper.OrderPayInfoMapper;
import com.lgy.oms.service.IOrderPayInfoService;
import org.springframework.stereotype.Service;

/**
 * 订单支付信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderPayInfoServiceImpl extends ServiceImpl<OrderPayInfoMapper, OrderPayInfo> implements IOrderPayInfoService {

    @Override
    public OrderPayInfo getByOrderId(String orderId) {
        QueryWrapper<OrderPayInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderPayInfo::getOrderId, orderId);
        return this.getOne(queryWrapper);
    }
}
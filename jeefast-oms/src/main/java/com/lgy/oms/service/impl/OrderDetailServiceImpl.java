package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderDetail;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.OrderDetailMapper;
import com.lgy.oms.service.IOrderDetailService;

import java.util.Collections;
import java.util.List;

/**
 * 订单明细信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

    @Override
    public boolean checkOrderCombDetailExist(OrderDetail orderDetail) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderDetail.getOrderId());
        queryWrapper.eq("source_row", orderDetail.getRowNumber());
        queryWrapper.select("order_id");
        List<OrderDetail> list = this.list(queryWrapper);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
}
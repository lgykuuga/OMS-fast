package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.domain.order.OrderStatusInfo;
import com.lgy.oms.mapper.OrderMainMapper;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    IOrderStatusService orderStatusService;

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
}
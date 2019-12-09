package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.system.incrementer.IDIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.OrderMainMapper;
import com.lgy.oms.service.IOrderMainService;

/**
 * 订单审核信息 服务层实现
 *
 * @author lgy
 * @date 2019-11-25
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {

    @Autowired
    IDIncrementer idIncrementer;


    @Override
    public OrderMain saveOrder(OrderMain orderMain) {

        String orderId = idIncrementer.getNextId(OrderModuleConstants.ORDERMAIN);
        System.out.println(orderId);

        return null;
    }
}
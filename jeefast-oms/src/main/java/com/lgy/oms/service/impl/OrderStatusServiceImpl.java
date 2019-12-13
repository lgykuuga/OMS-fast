package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderStatusInfo;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.OrderStatusMapper;
import com.lgy.oms.service.IOrderStatusService;

/**
 * 订单状态 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatusInfo> implements IOrderStatusService {

}
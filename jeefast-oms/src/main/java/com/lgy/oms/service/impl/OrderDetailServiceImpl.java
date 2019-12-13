package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderDetail;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.OrderDetailMapper;
import com.lgy.oms.service.IOrderDetailService;

/**
 * 订单明细信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
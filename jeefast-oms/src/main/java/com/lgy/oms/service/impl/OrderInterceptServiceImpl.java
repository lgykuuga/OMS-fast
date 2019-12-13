package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.OrderInterceptMapper;
import com.lgy.oms.service.IOrderInterceptService;

/**
 * 订单拦截信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderInterceptServiceImpl extends ServiceImpl<OrderInterceptMapper, OrderInterceptInfo> implements IOrderInterceptService {

}
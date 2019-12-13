package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.order.OrderBuyerInfo;
import com.lgy.oms.mapper.OrderBuyerInfoMapper;
import com.lgy.oms.service.IOrderBuyerInfoService;
import org.springframework.stereotype.Service;

/**
 * 订单买家信息 服务层实现
 *
 * @author lgy
 * @date 2019-12-13
 */
@Service
public class OrderBuyerInfoServiceImpl extends ServiceImpl<OrderBuyerInfoMapper, OrderBuyerInfo> implements IOrderBuyerInfoService {

}
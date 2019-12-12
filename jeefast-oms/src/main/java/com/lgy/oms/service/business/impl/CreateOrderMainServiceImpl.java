package com.lgy.oms.service.business.impl;


import com.lgy.base.domain.Commodity;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderDetailTypeEnum;
import com.lgy.oms.service.business.ICreateOrderMainService;
import com.lgy.oms.service.business.IOrderDetailProcessingService;
import com.lgy.system.incrementer.IDIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 创建主订单信息实现
 * @Author LGy
 * @Date 2019/12/10
 */
@Service
public class CreateOrderMainServiceImpl implements ICreateOrderMainService {

    /** 发号器 */
    @Autowired
    IDIncrementer idIncrementer;
    /** 商品档案 */
    @Autowired
    ICommodityService commodityService;
    /** 订单明细处理 */
    @Autowired
    IOrderDetailProcessingService orderDetailProcessingService;

    @Override
    public OrderMain saveOrder(OrderMain orderMain) {


        if (StringUtils.isEmpty(orderMain.getOrderId())) {
            //若不存在单号,则生成单据流水号
            String orderId = idIncrementer.getNextId(OrderModuleConstants.ORDERMAIN);
            orderMain.setOrderId(orderId);
        }

        //订单买家信息
        if (orderMain.getOrderBuyerinfo() != null) {
            orderMain.getOrderBuyerinfo().setOrderId(orderMain.getOrderId());
        }
        //订单支付信息
        if (orderMain.getOrderPayinfo() != null) {
            orderMain.getOrderPayinfo().setOrderId(orderMain.getOrderId());
        }
        //订单业务类型信息
        if (orderMain.getOrderTypeinfo() != null) {
            orderMain.getOrderTypeinfo().setOrderId(orderMain.getOrderId());
        }
        //订单状态类型信息
        if (orderMain.getOrderStatusinfo() != null) {
            orderMain.getOrderStatusinfo().setOrderId(orderMain.getOrderId());
        }
        //订单异常类型信息
        if (orderMain.getOrderInterceptInfo() != null) {
            orderMain.getOrderInterceptInfo().setOrderId(orderMain.getOrderId());
        }
        //订单明细信息
        if (StringUtils.isNotEmpty(orderMain.getOrderDetails())) {
            for (OrderDetail orderDetail : orderMain.getOrderDetails()) {
                orderDetail.setOrderId(orderMain.getOrderId());
            }
        }
        return orderMain;
    }
}

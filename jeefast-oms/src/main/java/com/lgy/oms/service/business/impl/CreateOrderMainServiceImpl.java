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
        /** 生成单据流水号 */
        String orderId = idIncrementer.getNextId(OrderModuleConstants.ORDERMAIN);

        if (StringUtils.isEmpty(orderMain.getOrderId())) {
            orderMain.setOrderId(orderId);
            //订单买家信息
            if (orderMain.getOrderBuyerinfo() != null) {
                orderMain.getOrderBuyerinfo().setOrderId(orderId);
            }
            //订单支付信息
            if (orderMain.getOrderPayinfo() != null) {
                orderMain.getOrderPayinfo().setOrderId(orderId);
            }
            //订单业务类型信息
            if (orderMain.getOrderTypeinfo() != null) {
                orderMain.getOrderTypeinfo().setOrderId(orderId);
            }
        }

        //订单明细信息
        if (StringUtils.isNotEmpty(orderMain.getOrderDetails())) {

            //新增的订单明细
            List<OrderDetail> addOrderDetails = new ArrayList<>();

            orderMain.getOrderDetails().forEach(orderDetail -> {

                orderDetail.setOrderId(orderMain.getOrderId());

                if (StringUtils.isNotEmpty(orderDetail.getCommodity())) {
                    /** 存在商品编码,解析组合商品 */
                    //关联商品档案
                    Commodity commodity = commodityService.getOne(orderDetail.getCommodity());
                    if (commodity != null) {
                        //普通商品
                        orderDetail.setType(OrderDetailTypeEnum.DEFAULT.getCode());

                        //判断是否组合商品
                        if (Constants.YES.equals(commodity.getCombo())) {
                            //解析组合商品
                            CommonResponse<List<OrderDetail>> analysisResp = orderDetailProcessingService.analysisCombCommodity(orderDetail);
                            if (Constants.SUCCESS.equals(analysisResp.getCode())) {
                                addOrderDetails.addAll(analysisResp.getData());
                                //组合商品
                                orderDetail.setType(OrderDetailTypeEnum.COMB.getCode());
                            }
                        }
                        //图片
                        orderDetail.setPicPath(commodity.getImgUrl());
                        //尺寸
                        orderDetail.setSize("");
                        //商品条码
                        orderDetail.setBarCode("");
                        //品牌
                        orderDetail.setBrand("");
                    } else {
                        //未解析商品
                        orderDetail.setType(OrderDetailTypeEnum.UNPARSED.getCode());
                    }
                }
            });

            if (StringUtils.isNotEmpty(addOrderDetails)) {
                orderMain.getOrderDetails().addAll(addOrderDetails);
            }
        }

        return orderMain;
    }
}

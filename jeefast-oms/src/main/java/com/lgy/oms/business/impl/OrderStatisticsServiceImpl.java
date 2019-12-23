package com.lgy.oms.business.impl;

import com.lgy.base.domain.Commodity;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderDetailTypeEnum;
import com.lgy.oms.enums.order.OrderSizeTypeEnum;
import com.lgy.oms.business.IOrderStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description 订单整理计算统计功能服务实现
 * @Author LGy
 * @Date 2019/12/12
 */
@Service
public class OrderStatisticsServiceImpl implements IOrderStatisticsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 商品档案
     */
    @Autowired
    ICommodityService commodityService;

    @Override
    public void orderStatisticsMethod(OrderMain orderMain) {

        List<OrderDetail> orderDetails = orderMain.getOrderDetails();

        if (StringUtils.isNotEmpty(orderDetails)) {

            //订单明细数量
            int size = 0;
            //统计SKU种类数量
            Set<String> skus = new HashSet<>();
            //订单明细总体积
            BigDecimal volume = BigDecimal.ZERO;
            //订单明细总重量
            BigDecimal weight = BigDecimal.ZERO;

            for (OrderDetail orderDetail : orderDetails) {
                //商品档案
                Commodity commodity = commodityService.getOne(orderDetail.getCommodity());

                if (commodity != null) {
                    //不统计组合商品
                    if (!OrderDetailTypeEnum.COMB.getCode().equals(orderDetail.getType())) {
                        //订单数量
                        size += orderDetail.getQty();

                        //体积
                        volume = volume.add(new BigDecimal(orderDetail.getQty())
                                .multiply(commodity.getVolume() == null ? BigDecimal.ZERO : commodity.getVolume()));
                        //重量
                        weight = weight.add(new BigDecimal(orderDetail.getQty())
                                .multiply(commodity.getWeight() == null ? BigDecimal.ZERO : commodity.getWeight()));
                    }
                    //图片
                    orderDetail.setPicPath(commodity.getImgUrl());
                    //尺寸
                    orderDetail.setSize(commodity.getSize());
                    //商品条码
                    orderDetail.setBarCode(commodity.getBarCode());
                    //品牌
                    orderDetail.setBrand(commodity.getBrand());
                    //sku种类
                    skus.add(commodity.getGco());
                }
            }

            //总件数
            orderMain.setQty(size);
            //总体积
            orderMain.setVolume(volume);
            //总重量
            orderMain.setWeight(weight);
            //sku种类数量
            orderMain.setSkuNum(skus.size());
            //商品编码集合
            String skuCollection = StringUtils.join(skus, ",");
            orderMain.setCommodity(skuCollection);

            /**设置订单数量类型 */
            if (size == 1) {
                //一单一货
                orderMain.setSizeType(OrderSizeTypeEnum.SINGLE_CARGO.getCode());
            } else if (size >= OrderSizeTypeEnum.QTY) {
                //一单一货
                orderMain.setSizeType(OrderSizeTypeEnum.BIG_CARGO.getCode());
            } else {
                //一单多货
                orderMain.setSizeType(OrderSizeTypeEnum.MULTIPLE_CARGO.getCode());
            }
        }

    }



}

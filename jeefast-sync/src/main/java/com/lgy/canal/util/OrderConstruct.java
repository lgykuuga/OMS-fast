package com.lgy.canal.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lgy.canal.constant.EsOrderTableConstant;
import com.lgy.model.distribution.EsDistributionDetail;
import com.lgy.model.distribution.EsDistributionOrder;
import com.lgy.model.order.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description 订单构建
 * @Author LGy
 * @Date 2020/5/8 11:29
 **/
public class OrderConstruct {


    /**
     * 从更新的数据中构建出ExtendOrderHeader
     */
    public static EsOrderMain getOrderFromMap(String tableName, Map modifyData) {

        JSONObject orderJson = new JSONObject(modifyData);

        EsOrderMain order = new EsOrderMain();

        if (EsOrderTableConstant.OMS_ORDER_MAIN.equals(tableName)) {

            order = JSONObject.toJavaObject(orderJson, EsOrderMain.class);

        } else if (EsOrderTableConstant.OMS_ORDER_PAYINFO.equals(tableName)) {

            EsOrderPayment esOrderPayment = JSONObject.toJavaObject(orderJson, EsOrderPayment.class);
            order.setOrderPayment(esOrderPayment);

        } else if (EsOrderTableConstant.OMS_ORDER_DETAIL.equals(tableName)) {

            EsOrderDetail esOrderItem = JSONObject.toJavaObject(orderJson, EsOrderDetail.class);
            order.setOrderDetails(Lists.newArrayList(esOrderItem));

        } else if (EsOrderTableConstant.OMS_ORDER_BUYERINFO.equals(tableName)) {

            EsOrderBuyer esOrderBuyer = JSONObject.toJavaObject(orderJson, EsOrderBuyer.class);
            order.setOrderBuyer(esOrderBuyer);

        } else if (EsOrderTableConstant.OMS_ORDER_STATUS.equals(tableName)) {

            EsOrderStatus esOrderStatus = JSONObject.toJavaObject(orderJson, EsOrderStatus.class);
            order.setOrderStatus(esOrderStatus);

        } else if (EsOrderTableConstant.OMS_ORDER_TYPEINFO.equals(tableName)) {

            EsOrderType esOrderType = JSONObject.toJavaObject(orderJson, EsOrderType.class);
            order.setOrderType(esOrderType);
        }

        return order;
    }


    /**
     * 根据表名获取需要的字段
     */
    public static List getEsFields(String tableName) {
        switch (tableName) {
            //订单
            case EsOrderTableConstant.OMS_ORDER_MAIN:
                return EsFieldUtils.getEsFields(EsOrderMain.class);
            case EsOrderTableConstant.OMS_ORDER_BUYERINFO:
                return EsFieldUtils.getEsFields(EsOrderBuyer.class);
            case EsOrderTableConstant.OMS_ORDER_DETAIL:
                return EsFieldUtils.getEsFields(EsOrderDetail.class);
            case EsOrderTableConstant.OMS_ORDER_PAYINFO:
                return EsFieldUtils.getEsFields(EsOrderPayment.class);
            case EsOrderTableConstant.OMS_ORDER_STATUS:
                return EsFieldUtils.getEsFields(EsOrderStatus.class);
            case EsOrderTableConstant.OMS_ORDER_TYPEINFO:
                return EsFieldUtils.getEsFields(EsOrderType.class);
            //配货单
            case EsOrderTableConstant.OMS_DISTRIBUTION_DETAIL:
                return EsFieldUtils.getEsFields(EsDistributionDetail.class);
            case EsOrderTableConstant.OMS_DISTRIBUTION_ORDER:
                return EsFieldUtils.getEsFields(EsDistributionOrder.class);
            default:
                return Collections.emptyList();
        }
    }


}

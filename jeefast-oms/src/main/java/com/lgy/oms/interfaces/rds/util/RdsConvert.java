package com.lgy.oms.interfaces.rds.util;

import com.lgy.oms.domain.ShopInterfaces;
import com.lgy.oms.enums.order.OrderDetailRefundStatusEnum;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrderDetail;
import com.lgy.oms.interfaces.rds.bean.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description 跨境翼下载对象转换成标准订单对象
 * @Author LGy
 * @Date 2019/12/2 15:22
 **/
public class RdsConvert {

    public static StandardOrder changeStandard(RdsTradeMain tradeMain, ShopInterfaces shopInterfaces) {
        //标准订单
        StandardOrder standardOrder = new StandardOrder();

        if (tradeMain.getTrade_fullinfo_get_response() != null
                && tradeMain.getTrade_fullinfo_get_response().getTrade() != null) {
            RdsTrade trade = tradeMain.getTrade_fullinfo_get_response().getTrade();

            BeanUtils.copyProperties(trade, standardOrder);
            //店铺编码
            standardOrder.setShop(shopInterfaces.getShop());
            //平台编码
            standardOrder.setPlatform(shopInterfaces.getPlatform());
            //货主编码
            standardOrder.setOwner(shopInterfaces.getOwner());
            //平台发货
            standardOrder.setPlatform_send(true);
            //货到付款
            standardOrder.setCod(false);
            //存在发票申请
            standardOrder.setInvoice(false);
            //订单明细信息
            RdsOrders orders = trade.getOrders();
            if (orders != null && orders.getOrder() != null && !orders.getOrder().isEmpty()) {

                List<StandardOrderDetail> details = new ArrayList<>(orders.getOrder().size());
                for (RdsOrder order : orders.getOrder()) {
                    StandardOrderDetail detail = new StandardOrderDetail();

                    BeanUtils.copyProperties(order, detail);

                    //无申请退款
                    detail.setRefund_status(OrderDetailRefundStatusEnum.NO_REFUND.name());

                    details.add(detail);
                }

                standardOrder.setOrderDetails(details);
            }
        }


        return standardOrder;
    }
}

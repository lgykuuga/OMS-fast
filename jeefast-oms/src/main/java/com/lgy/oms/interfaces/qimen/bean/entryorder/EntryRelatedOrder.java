package com.lgy.oms.interfaces.qimen.bean.entryorder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 关联订单信息
 *
 * @author LGy.
 */
@XStreamAlias("relateOrder")
public class EntryRelatedOrder {

    /**
     * 关联的订单类型，CG=采购单，DB=调拨单, CK=出库单，RK=入库单，string (50) ,  (只传英文编码)
     */
    private String orderType;

    /**
     * 关联的订单编号
     */
    private String orderCode;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}

package com.lgy.oms.interfaces.qimen.bean.order;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单据挂起（恢复）
 *
 * @author LGy
 */
@XStreamAlias("request")
public class OrderPendingRequest {
    /**
     * 操作类型(pending=挂起;restore=恢复)
     */
    private String actionType;
    /**
     * 仓库编码(统仓统配等无需ERP指定仓储编码的情况填OTHER)
     */
    private String warehouseCode;
    /**
     * 货主编码
     */
    private String ownerCode;
    /**
     * 单据编码
     */
    private String orderCode;
    /**
     * 仓储系统单据编码
     */
    private String orderId;
    /**
     * 单据类型, 必填,  JYCK= 一般交易出库单，HHCK= 换货出库 ，BFCK= 补发出库 PTCK=普通出库单，DBCK=调拨出库 ，
     * QTCK=其他出库， CGRK=采购入库 ，DBRK= 调拨入库 ，QTRK= 其他入库 ，XTRK= 销退入库 HHRK= 换货入库 CNJG= 仓内加工单
     */
    private String orderType;
    /**
     * 挂起原因
     */
    private String reason;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

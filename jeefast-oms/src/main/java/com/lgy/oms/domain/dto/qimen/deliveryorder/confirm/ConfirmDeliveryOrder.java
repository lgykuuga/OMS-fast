package com.lgy.oms.domain.dto.qimen.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 发货确认接口:发货单信息
 *
 * @author LGy
 */
@XStreamAlias("deliveryOrder")
public class ConfirmDeliveryOrder {

    /**
     * 出库单号
     */
    private String deliveryOrderCode;
    /**
     * 仓储系统出库单号
     */
    private String deliveryOrderId;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 出库单类型(JYCK=一般交易出库单, HHCK=换货出库单, BFCK=补发出库单，QTCK=其他出库单)
     */
    private String orderType;

    /**
     * 出库单状态(NEW-未开始处理;ACCEPT-仓库接单;PARTDELIVERED-部分发货完成;DELIVERED-发货完成;EXCEPTION-异 常;CANCELED-取消;CLOSED-关闭;REJECT-拒单;CANCELEDFAIL-取消失败;只传英文编 码)
     */
    private String status;
    /**
     * 外部业务编码(消息ID;用于去重;ISV对于同一请求;分配一个唯一性的编码。用来保证因为网络等原因导致重复传输;请求 不会被重复处理;条件必填;条件为一单需要多次确认时)
     */
    private String outBizCode;
    /**
     * 支持出库单多次发货(多次发货后确认时;0表示发货单最终状态确认;1表示发货单中间状态确认)
     */
    private String confirmType;
    /**
     * 订单完成时间(YYYY-MM-DD HH:MM:SS)
     */
    private String orderConfirmTime;
    /**
     * 操作员 (审核员) 编码
     */
    private String operatorCode;
    /**
     * 操作员 (审核员) 名称
     */
    private String operatorName;
    /**
     * 当前状态操作时间(YYYY-MM-DD HH:MM:SS)
     */
    private String operateTime;
    /**
     * 仓储费用
     */
    private String storageFee;
    /**
     * 发票信息
     */
    private List<ConfirmInvoice> invoices;

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getDeliveryOrderId() {
        return deliveryOrderId;
    }

    public void setDeliveryOrderId(String deliveryOrderId) {
        this.deliveryOrderId = deliveryOrderId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public String getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType;
    }

    public String getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(String orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(String storageFee) {
        this.storageFee = storageFee;
    }

    public List<ConfirmInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<ConfirmInvoice> invoices) {
        this.invoices = invoices;
    }
}

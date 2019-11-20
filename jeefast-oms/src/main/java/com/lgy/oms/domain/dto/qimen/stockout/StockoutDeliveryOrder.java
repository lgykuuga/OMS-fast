package com.lgy.oms.domain.dto.qimen.stockout;

import com.lgy.oms.domain.dto.qimen.deliveryorder.create.CreatePickerInfo;
import com.lgy.oms.domain.dto.qimen.deliveryorder.create.CreateReceiverInfo;
import com.lgy.oms.domain.dto.qimen.deliveryorder.create.CreateRelatedOrder;
import com.lgy.oms.domain.dto.qimen.deliveryorder.create.CreateSenderInfo;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 出库单信息
 *
 * @author LGy.
 */
@XStreamAlias("deliveryOrder")
public class StockoutDeliveryOrder {

    /**
     * 单据总行数(当单据需要分多个请求发送时;发送方需要将totalOrderLines填入;接收方收到后;根据实际接收到的 条数和 totalOrderLines进行比对;如果小于;则继续等待接收请求。如果等于;则表示该单据的所有请求发送完 成)
     */
    private Long totalOrderLines;
    /**
     * 出库单号
     */
    private String deliveryOrderCode;
    /**
     * 仓储系统出库单号
     */
    private String deliveryOrderId;
    /**
     * 外部业务编码, 外部业务编码, 同一请求的唯一性标示编码。ISV对于同一请求，分配一个唯一性的编码。用来保证因为网络等原因导致重复传输，请求不会被重复处理，条件必填，条件为一单需要多次确认时
     */
    private String outBizCode;
    /**
     * 出库单类型(JYCK=一般交易出库单, HHCK=换货出库单, BFCK=补发出库单，QTCK=其他出库单)
     */
    private String orderType;
    /**
     * 0 表示发货单最终状态确认；
     * 1 表示发货单中间状态确认；
     */
    private String confirmType;
    /**
     * 订单完成时间
     */
    private String orderConfirmTime;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 发货单创建时间 YYYY-MM-DD HH:MM:SS
     */
    private String createTime;
    /**
     * 要求出库时间
     */
    private String scheduleDate;
    /**
     * 物流公司名称（包括干线物流公司等）
     */
    private String logisticsName;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 提货方式（到仓自提，快递，干线物流）
     */
    private String transportMode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 提货人信息
     */
    private CreatePickerInfo pickerInfo;
    /**
     * 收件人信息
     */
    private CreateReceiverInfo receiverInfo;
    /**
     * 发件人信息
     */
    private CreateSenderInfo senderInfo;
    /**
     * 入库单状态, string (50) ,  必填 (NEW-未开始处理,
     * ACCEPT-仓库接单 , PARTFULFILLED-部分收货完成,
     * FULFILLED-收货完成,  EXCEPTION-异常,  CANCELED-取消,
     * CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败) ,  (只传英文编码)
     */
    private String status;

    /**
     * 货主编码,  string (50)
     */
    private String ownerCode;

    /**
     * 物流商编码
     */
    private String logisticsCode;
    /**
     * 运单号
     */
    private String expressCode;
    /**
     * 关联的订单单据类型和单据号，如采购单、调拨单等
     **/
    private List<CreateRelatedOrder> relatedOrders;

    public Long getTotalOrderLines() {
        return totalOrderLines;
    }

    public void setTotalOrderLines(Long totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public List<CreateRelatedOrder> getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(List<CreateRelatedOrder> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CreatePickerInfo getPickerInfo() {
        return pickerInfo;
    }

    public void setPickerInfo(CreatePickerInfo pickerInfo) {
        this.pickerInfo = pickerInfo;
    }

    public CreateReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(CreateReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public CreateSenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(CreateSenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(String orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }
}

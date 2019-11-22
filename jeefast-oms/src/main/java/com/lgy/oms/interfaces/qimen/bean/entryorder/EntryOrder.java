package com.lgy.oms.interfaces.qimen.bean.entryorder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * 入库单信息
 *
 * @author LGy.
 */
@XStreamAlias("entryOrder")
public class EntryOrder {

    /**
     * 单据总行数(当单据需要分多个请求发送时;
     * 发送方需要将totalOrderLines填入;接收方收到后;
     * 根据实际接收到的 条数和 totalOrderLines进行比对;如果小于;
     * 则继续等待接收请求。如果等于;则表示该单据的所有请求发送完 成)
     */
    private Long totalOrderLines;
    /**
     * 货主
     */
    private String ownerCode;
    /**
     * 入库单号
     */
    private String entryOrderCode;
    /**
     * 采购单号
     */
    private String purchaseOrderCode;
    /**
     * 仓储系统入库单ID
     */
    private String entryOrderId;
    /**
     * 外部业务编码, 消息ID, 用于去重, ISV对于同一请求
     */
    private String outBizCode;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     *
     */
    private String confirmType;
    /**
     * 订单创建时间
     */
    private String orderCreateTime;
    /**
     * 业务类型 (CGRK=采购入库, DBRK=调拨入库, QTRK=其他入库) , string (50) ,  (只传英文编码)
     */
    private String orderType;
    /**
     * 入库单状态, string (50) ,  必填 (NEW-未开始处理,  ACCEPT-仓库接单 , PARTFULFILLED-部分收货完成,  FULFILLED-收货完成,  EXCEPTION-异常,  CANCELED-取消,  CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败) ,  (只传英文编码)
     */
    private String entryOrderType;
    /**
     * 入库单状态, string (50) ,  必填 (NEW-未开始处理,  ACCEPT-仓库接单 , PARTFULFILLED-部分收货完成,  FULFILLED-收货完成,  EXCEPTION-异常,  CANCELED-取消,  CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败) ,  (只传英文编码)
     */
    private String status;
    /**
     * 预期到货时间
     */
    private String expectStartTime;
    /**
     * 最迟预期到货时间
     */
    private String expectEndTime;
    /**
     * 物流公司编码, string (50) , SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、POSTB=邮政小包  、GTO=国通、YUNDA=韵达, OTHER=其他，必填,  (只传英文编码)
     */
    private String logisticsCode;
    /**
     * 物流公司名称
     */
    private String logisticsName;
    /**
     * 运单号
     */
    private String expressCode;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 操作员编码
     */
    private String operatorCode;
    /**
     * 操作员名称
     */
    private String operatorName;
    /**
     * 操作时间
     */
    private String operateTime;
    /**
     * 备注
     */
    private String remark;

    /**
     * 收货人信息
     */
    private EntryReceiverInfo receiverInfo;
    /**
     * 发件人信息
     */
    private EntrySenderInfo senderInfo;

    /*
     * <!--关联的订单单据类型和单据号，如采购单、调拨单等-->
     * **/
    private List<EntryRelatedOrder> relatedOrders;

    public Long getTotalOrderLines() {
        return totalOrderLines;
    }

    public void setTotalOrderLines(Long totalOrderLines) {
        this.totalOrderLines = totalOrderLines;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getConfirmType() {
        return confirmType;
    }

    public List<EntryRelatedOrder> getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(List<EntryRelatedOrder> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType;
    }

    public EntryReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(EntryReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public EntrySenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(EntrySenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
    }

    public String getEntryOrderCode() {
        return this.entryOrderCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return this.warehouseCode;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderCreateTime() {
        return this.orderCreateTime;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setExpectStartTime(String expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public String getExpectStartTime() {
        return this.expectStartTime;
    }

    public void setExpectEndTime(String expectEndTime) {
        this.expectEndTime = expectEndTime;
    }

    public String getExpectEndTime() {
        return this.expectEndTime;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsCode() {
        return this.logisticsCode;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsName() {
        return this.logisticsName;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressCode() {
        return this.expressCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return this.supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorCode() {
        return this.operatorCode;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateTime() {
        return this.operateTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getEntryOrderType() {
        return entryOrderType;
    }

    public void setEntryOrderType(String entryOrderType) {
        this.entryOrderType = entryOrderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntryOrderId() {
        return entryOrderId;
    }

    public void setEntryOrderId(String entryOrderId) {
        this.entryOrderId = entryOrderId;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }
}

package com.lgy.oms.interfaces.qimen.bean.returnorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 退货单信息
 *
 * @author LGy
 */
@XStreamAlias("returnOrder")
public class CreateReturnOrder {
    /**
     * 退货单编码
     */
    private String returnOrderCode;
    /**
     * 仓库系统订单编码
     */
    private String returnOrderId;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 业务类型 (CGRK=采购入库, DBRK=调拨入库, QTRK=其他入库) , string (50) ,  (只传英文编码)
     */
    private String orderType;
    /**
     * 确认入库时间, string (19) ,YYYY-MM-DD
     */
    private String orderConfirmTime;
    /**
     * 用字符串格式来表示订单标记列表：比如VISIT^ SELLER_AFFORD^SYNC_RETURN_BILL 等, 中间用“^”来隔开 订单标记list (所有字母全部大写) ： VISIT=上门；SELLER_AFFORD=是否卖家承担运费 (默认是) ；SYNC_RETURN_BILL=同时退回发票；
     */
    private String orderFlag;
    /**
     * 原出库单号（ERP分配）, string(50) ,必填
     */
    private String preDeliveryOrderCode;
    /**
     * 原出库单号（WMS分配）, string (50)，条件必填
     */
    private String preDeliveryOrderId;
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
     * 退货原因, string (200)
     */
    private String returnReason;
    /**
     * 买家昵称
     */
    private String buyerNick;
    /**
     * 备注
     */
    private String remark;
    /**
     * 外部业务编码
     */
    private String outBizCode;

    /**
     * 发件人信息
     */
    private CreateReturnOrderSenderInfo senderInfo;

    /**
     * 货主编码,  string (50)
     */
    private String ownerCode;


    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public String getPreDeliveryOrderId() {
        return preDeliveryOrderId;
    }

    public void setPreDeliveryOrderId(String preDeliveryOrderId) {
        this.preDeliveryOrderId = preDeliveryOrderId;
    }

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getReturnOrderCode() {
        return returnOrderCode;
    }

    public void setReturnOrderCode(String returnOrderCode) {
        this.returnOrderCode = returnOrderCode;
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

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public CreateReturnOrderSenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(CreateReturnOrderSenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(String orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }

}

package com.lgy.oms.interfaces.qimen.bean.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 创建发货单信息
 *
 * @author LGy
 */
@XStreamAlias("deliveryOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateDeliveryOrder {

    /**
     * 出库单号
     */
    private String deliveryOrderCode;
    /**
     * 原出库单号（ERP分配）, string (50) , 条件必填，条件为换货出库
     */
    private String preDeliveryOrderCode;
    /**
     * 原出库单号（WMS分配）, string (50) , 条件必填，条件为换货出库
     */
    private String preDeliveryOrderId;
    /**
     * 出库单类型(JYCK=一般交易出库单, HHCK=换货出库单, BFCK=补发出库单，QTCK=其他出库单)
     */
    private String orderType;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 订单标记 ，用字符串格式来表示订单标记列表： 比如COD, 中间用“^”来隔开，string (200) ，
     * COD =货到付款 , LIMIT=限时配送 , PRESELL=预售 , COMPLAIN=已投诉 , SPLIT=拆单,  EXCHANGE=换货,  VISIT=上门 ,  MODIFYTRANSPORT=是否可改配送方式,  是否可改配送方式 默认可更改 , CONSIGN =物流宝代理发货, 自动更改发货状态 SELLER_AFFORD =是否卖家承担运费 默认是, 即没 ,  FENXIAO=分销订单
     */
    private String orderFlag;
    /**
     * 来源平台编码
     * TB= 淘宝 、TM=天猫 、JD=京东、DD=当当、PP=拍拍、YX=易讯、EBAY=ebay、QQ=QQ网购、AMAZON=亚马逊、SN=苏宁、GM=国美、WPH=唯品会、JM=聚美、LF=乐蜂、MGJ=蘑菇街、JS=聚尚、PX=拍鞋、YT=银泰、YHD=1号店、FK=凡客、
     * YL=邮乐、YG=优购、1688=阿里巴巴、POS=POS门店、OTHER=其他,  (只传英文编码)
     */
    private String sourcePlatformCode;
    /**
     * 来源平台名称
     */
    private String sourcePlatformName;
    /**
     * 发货单创建时间 YYYY-MM-DD HH:MM:SS
     */
    private String createTime;
    /**
     * 前台订单 (店铺订单) 创建时间 (下单时间) , string (19) , YYYY-MM-DD HH:MM:SS, 必填
     */
    private String placeOrderTime;
    /**
     * 支付时间(yyyy/MM/dd HH:mm:ss)
     */
    private String payTime;
    /**
     * 支付平台交易号, string(50), 淘系订单传支付宝交易号
     */
    private String payNo;
    /**
     * 操作员 (审核员) 编码
     */
    private String operatorCode;
    /**
     * 操作员 (审核员) 名称
     */
    private String operatorName;
    /**
     * 操作 (审核) 时间
     */
    private String operateTime;
    /**
     * 店铺编码
     */
    private String shopCode;
    /**
     * 店铺名称
     */
    private String shopNick;
    /**
     * 卖家名称
     */
    private String sellerNick;
    /**
     * 买家昵称
     */
    private String buyerNick;
    /**
     * 订单总金额(订单总金额=应收金额+已收金额=商品总金额-订单折扣金额+快递费用 ;单位 元)
     */
    private String totalAmount;
    /**
     * 商品总金额 (元)
     */
    private String itemAmount;
    /**
     * 订单折扣金额 (元)
     */
    private String discountAmount;
    /**
     * 快递费用 (元)
     */
    private String freight;
    /**
     * 应收金额(消费者还需要支付多少--货到付款时消费者还需要支付多少约定使用这个字 段;单位元 )
     */
    private String arAmount;
    /**
     * 已收金额(消费者已经支付多少;单位元)
     */
    private String gotAmount;
    /**
     * COD服务费
     */
    private String serviceFee;
    /**
     * 物流公司编码, string (50) , SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、
     * HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、POSTB=邮政小包  、GTO=国通、YUNDA=韵达, OTHER=其他，必填,  (只传英文编码)
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
     * 快递区域编码, 大头笔信息
     */
    private String logisticsAreaCode;
    /**
     * 配送要求
     */
    private CreateDeliveryRequirements deliveryRequirements;
    /**
     * 发件人信息
     */
    private CreateSenderInfo senderInfo;
    /**
     * 收件人信息
     */
    private CreateReceiverInfo receiverInfo;
    /**
     * 是否紧急, Y/N, 默认为N
     */
    private String isUrgency;
    /**
     * 是否需要发票, Y/N, 默认为N
     */
    private String invoiceFlag;
    /**
     * 发票信息
     */
    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private List<CreateInvoice> invoices;
    /**
     * 是否需要保险, Y/N, 默认为N
     */
    private String insuranceFlag;
    /**
     * 保险信息
     */
    private CreateInsurance insurance;
    /**
     * 买家留言
     */
    private String buyerMessage;
    /**
     * 卖家留言
     */
    private String sellerMessage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 服务编码
     */
    private String serviceCode;

    public String getDeliveryOrderCode() {
        return deliveryOrderCode;
    }

    public void setDeliveryOrderCode(String deliveryOrderCode) {
        this.deliveryOrderCode = deliveryOrderCode;
    }

    public String getPreDeliveryOrderCode() {
        return preDeliveryOrderCode;
    }

    public void setPreDeliveryOrderCode(String preDeliveryOrderCode) {
        this.preDeliveryOrderCode = preDeliveryOrderCode;
    }

    public String getPreDeliveryOrderId() {
        return preDeliveryOrderId;
    }

    public void setPreDeliveryOrderId(String preDeliveryOrderId) {
        this.preDeliveryOrderId = preDeliveryOrderId;
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

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getSourcePlatformCode() {
        return sourcePlatformCode;
    }

    public void setSourcePlatformCode(String sourcePlatformCode) {
        this.sourcePlatformCode = sourcePlatformCode;
    }

    public String getSourcePlatformName() {
        return sourcePlatformName;
    }

    public void setSourcePlatformName(String sourcePlatformName) {
        this.sourcePlatformName = sourcePlatformName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlaceOrderTime() {
        return placeOrderTime;
    }

    public void setPlaceOrderTime(String placeOrderTime) {
        this.placeOrderTime = placeOrderTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
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

    public String getShopNick() {
        return shopNick;
    }

    public void setShopNick(String shopNick) {
        this.shopNick = shopNick;
    }

    public String getSellerNick() {
        return sellerNick;
    }

    public void setSellerNick(String sellerNick) {
        this.sellerNick = sellerNick;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getArAmount() {
        return arAmount;
    }

    public void setArAmount(String arAmount) {
        this.arAmount = arAmount;
    }

    public String getGotAmount() {
        return gotAmount;
    }

    public void setGotAmount(String gotAmount) {
        this.gotAmount = gotAmount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
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

    public String getLogisticsAreaCode() {
        return logisticsAreaCode;
    }

    public void setLogisticsAreaCode(String logisticsAreaCode) {
        this.logisticsAreaCode = logisticsAreaCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public CreateDeliveryRequirements getDeliveryRequirements() {
        return deliveryRequirements;
    }

    public void setDeliveryRequirements(CreateDeliveryRequirements deliveryRequirements) {
        this.deliveryRequirements = deliveryRequirements;
    }

    public CreateSenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(CreateSenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public CreateReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(CreateReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public String getIsUrgency() {
        return isUrgency;
    }

    public void setIsUrgency(String isUrgency) {
        this.isUrgency = isUrgency;
    }

    public String getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(String invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
    }

    public List<CreateInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<CreateInvoice> invoices) {
        this.invoices = invoices;
    }

    public String getInsuranceFlag() {
        return insuranceFlag;
    }

    public void setInsuranceFlag(String insuranceFlag) {
        this.insuranceFlag = insuranceFlag;
    }

    public CreateInsurance getInsurance() {
        return insurance;
    }

    public void setInsurance(CreateInsurance insurance) {
        this.insurance = insurance;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getSellerMessage() {
        return sellerMessage;
    }

    public void setSellerMessage(String sellerMessage) {
        this.sellerMessage = sellerMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}

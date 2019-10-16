package com.lgy.oms.domain.dto.ods.response.order;

import java.util.List;

/**
 * @Description 订单主体
 * @Author LGy
 * @Date 2019/10/14
 */
public class SaleOrderBean {

    /*
     * 订单号
     */
    private String orderCode;
    /*
     * 订单类型
     *  必填, JYDD=一般交易订单(默认), HHDD=换货订单, BFDD=补发订单，QTDD=其他订单
     */
    private String orderType;
    /*
     *  订单标记 ，
     *  比如COD，string (200) ， COD =货到付款 , LIMIT=限时配送 , PRESELL=预售 , COMPLAIN=已投诉 , SPLIT=拆单,  EXCHANGE=换货,  VISIT=上门 ,  MODIFYTRANSPORT=是否可改配送方式,  是否可改配送方式 默认可更改 , CONSIGN =物流宝代理发货, 自动更改发货状态 SELLER_AFFORD =是否卖家承担运费 默认是, 即没 ,  FENXIAO=分销订单
     */
    private String orderFlag;
    /*
     *来源平台名称
     */
    private String platFromName;
    /*
     *来源平台编码
     * 必填,TB= 淘宝 、TM=天猫 、JD=京东、DD=当当、PP=拍拍、YX=易讯、EBAY=ebay、QQ=QQ网购、AMAZON=亚马逊、SN=苏宁、GM=国美、WPH=唯品会、JM=聚美、LF=乐蜂、MGJ=蘑菇街、JS=聚尚、PX=拍鞋、YT=银泰、YHD=1号店、VANCL=凡客、YL=邮乐、YG=优购、1688=阿里巴巴、POS=POS门店、OTHER=其他,  (只传英文编码)
     */
    private String platformCode;

    /*
     * 订单创建时间, string (19) , YYYY-MM-DD HH:MM:SS, 必填
     */
    private String createTime;
    /*
     * 订单支付时间, string (19) , YYYY-MM-DD HH:MM:SS,在线支付类型订单，必填
     */
    private String payTime;
    /*
     * 订单更新时间, string (19) , YYYY-MM-DD HH:MM:SS
     */
    private String updateTime;
    /*
     * 支付类型:在线支付、线下支付、积分支付、信用卡支付、礼品卡支付、支付宝支付
     */
    private String payType;
    /*
     * 支付平台交易号, string(50), 淘系订单传支付宝交易号
     */
    private String payNo;
    /*
     * 订单状态 , string (50) , WAIT_BUYER_PAY(等待买家付款),WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款), WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货),TRADE_BUYER_SIGNED(买家已签收,货到付款专用),TRADE_FINISHED(交易成功),TRADE_CLOSED(付款以后用户退款成功，交易自动关闭)
     */
    private String orderStatus;
    /*
     * 店铺名称, string (200) , 必填
     */
    private String shopNick;
    /*
     * 买家昵称, string (200)
     */
    private String buyerNick;
    /*
     * 订单总金额 (元) , double (18, 2) , 订单总金额=应收金额+已收金额=商品总金额-订单折扣金额+快递费用
     */
    private Double totalAmount;
    /*
     * 商品总金额 (元) , double (18, 2)
     */
    private Double itemAmount;
    /*
     * 订单折扣金额 (元) , double (18, 2)
     */
    private Double discountAmount;
    /*
     * 快递费用 (元) , double (18, 2)
     */
    private Double freight;
    /*
     * 应收金额 (元) , 消费者还需要支付多少（货到付款时消费者还需要支付多少约定使用这个字段）, double (18, 2)
     */
    private Double arAmount;
    /*
     * 已收金额 (元) , 消费者已经支付多少, double (18, 2)
     */
    private Double gotAmount;
    /*
     * 物流公司编码, string (50) , SF=顺丰、EMS=标准快递、EYB=经济快件、ZJS=宅急送、YTO=圆通  、ZTO=中通 (ZTO) 、HTKY=百世汇通、UC=优速、STO=申通、TTKDEX=天天快递  、QFKD=全峰、FAST=快捷、POSTB=邮政小包  、GTO=国通、YUNDA=韵达、JD=京东配送、DD=当当宅配、OTHER=其他，必填,  (只传英文编码)
     */
    private String logisticsCode;
    /*
     * 物流公司名称, string (200)
     */
    private String logisticsName;
    /*
     * 是否需要发票, Y/N, 默认为N
     */
    private Boolean invoiceFlag;
    /*
     * 买家留言, string (500)
     */
    private String buyerMessage;
    /*
     * 卖家留言, string (500)
     */
    private String sellerMessage;
    /*
     * 买家备注旗帜（与淘宝网上订单的买家备注旗帜对应，只有买家才能查看该字段）红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
     */
    private String buyerFlag;
    /*
     * 卖家备注旗帜（与淘宝网上订单的卖家备注旗帜对应，只有卖家才能查看该字段）红、黄、绿、蓝、紫 分别对应 1、2、3、4、5
     */
    private String sellerFlag;

    /**
     * 交易内部来源。WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算)一笔订单可能同时有以上多个标记，则以逗号分隔
     */
    private String tradeFrom;

    /*
     * 备注，string（500）
     */
    private String remark;

    /*
     * 订单明细
     */
    private List<SaleOrderLinesBean> detail;
    /*
     * 地址信息
     */
    private ReceiverInfoBean receiver;
    /*
     * 发票信息
     */
    private InvoiceBean invoice;
    /*
     * 配送要求
     */
    private DeliveryRequirementsBean deliveryRequirements;
    /*
     * 优惠信息
     */
    private List<CouponDetailBean> couponDetailList;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public String getPlatFromName() {
        return platFromName;
    }

    public void setPlatFromName(String platFromName) {
        this.platFromName = platFromName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShopNick() {
        return shopNick;
    }

    public void setShopNick(String shopNick) {
        this.shopNick = shopNick;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getArAmount() {
        return arAmount;
    }

    public void setArAmount(Double arAmount) {
        this.arAmount = arAmount;
    }

    public Double getGotAmount() {
        return gotAmount;
    }

    public void setGotAmount(Double gotAmount) {
        this.gotAmount = gotAmount;
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

    public Boolean getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(Boolean invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
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

    public List<SaleOrderLinesBean> getDetail() {
        return detail;
    }

    public void setDetail(List<SaleOrderLinesBean> detail) {
        this.detail = detail;
    }

    public ReceiverInfoBean getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiverInfoBean receiver) {
        this.receiver = receiver;
    }

    public InvoiceBean getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceBean invoice) {
        this.invoice = invoice;
    }

    public DeliveryRequirementsBean getDeliveryRequirements() {
        return deliveryRequirements;
    }

    public void setDeliveryRequirements(DeliveryRequirementsBean deliveryRequirements) {
        this.deliveryRequirements = deliveryRequirements;
    }

    public List<CouponDetailBean> getCouponDetailList() {
        return couponDetailList;
    }

    public void setCouponDetailList(List<CouponDetailBean> couponDetailList) {
        this.couponDetailList = couponDetailList;
    }

    public String getSellerFlag() {
        return sellerFlag;
    }

    public void setSellerFlag(String sellerFlag) {
        this.sellerFlag = sellerFlag;
    }

    public String getBuyerFlag() {
        return buyerFlag;
    }

    public void setBuyerFlag(String buyerFlag) {
        this.buyerFlag = buyerFlag;
    }

    public String getTradeFrom() {
        return tradeFrom;
    }

    public void setTradeFrom(String tradeFrom) {
        this.tradeFrom = tradeFrom;
    }
}

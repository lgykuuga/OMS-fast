package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单审核信息表 oms_order_audit
 *
 * @author lgy
 * @date 2019-10-22
 */
@TableName("oms_order_main")
public class OrderMain extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 订单流水编号
     */
    private String biid;

    /**
     * 来源单号
     */
    private String tid;

    /**
     * 店铺编码
     */
    private String shop;

    /**
     * 货主编码
     */
    private String owner;

    /**
     * 订单是否冻结
     */
    private Integer frozen;

    /**
     * 是否参与活动
     */
    private Integer active;

    /**
     * 是否人工编辑
     */
    private Integer handEdit;

    /**
     * 是否退款
     */
    private Integer refund;

    /**
     * 是否拦截
     */
    private Integer intercept;

    /**
     * 是否售后
     */
    private Integer aftersales;

    /**
     * 是否发票
     */
    private Integer invoice;

    /**
     * 是否用户锁定
     */
    private Integer orderLock;

    /**
     * 锁定人编码
     */
    private String lockUser;

    /**
     * 锁定时间
     */
    private Date lockTime;

    /**
     * 订单标记
     */
    private String mark;

    /**
     * 标记内容
     */
    private String markContent;

    /**
     * 卖家备注旗帜
     */
    private String sellerFlag;

    /**
     * 尺码类型
     */
    private Integer sizeType;

    /**
     * sku种类数量
     */
    private Integer skuNum;

    /**
     * 总件数
     */
    private Integer qty;

    /**
     * 商品编码集合
     */
    private String commodity;

    /**
     * 总体积
     */
    private BigDecimal volume;

    /**
     * 总重量
     */
    private BigDecimal weight;

    /**
     * 发货仓库编码(支持多条)
     */
    private String warehouse;

    /**
     * 物流商编码(支持多条)
     */
    private String logistics;

    /**
     * 快递单号(支持多条)
     */
    private String expressNumber;

    /**
     * 发货时间
     */
    private Date sendoutTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单买家信息
     */
    private OrderBuyerinfo orderBuyerinfo;

    /**
     * 订单支付信息
     */
    private OrderPayinfo orderPayinfo;

    /**
     * 订单业务类型信息
     */
    private OrderTypeinfo orderTypeinfo;

    /**
     * 订单明细信息
     */
    private List<OrderDetail> orderDetails;

    public OrderBuyerinfo getOrderBuyerinfo() {
        return orderBuyerinfo;
    }

    public void setOrderBuyerinfo(OrderBuyerinfo orderBuyerinfo) {
        this.orderBuyerinfo = orderBuyerinfo;
    }

    public OrderPayinfo getOrderPayinfo() {
        return orderPayinfo;
    }

    public void setOrderPayinfo(OrderPayinfo orderPayinfo) {
        this.orderPayinfo = orderPayinfo;
    }

    public OrderTypeinfo getOrderTypeinfo() {
        return orderTypeinfo;
    }

    public void setOrderTypeinfo(OrderTypeinfo orderTypeinfo) {
        this.orderTypeinfo = orderTypeinfo;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getBiid() {
        return biid;
    }

    public void setBiid(String biid) {
        this.biid = biid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getFrozen() {
        return frozen;
    }

    public void setFrozen(Integer frozen) {
        this.frozen = frozen;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getHandEdit() {
        return handEdit;
    }

    public void setHandEdit(Integer handEdit) {
        this.handEdit = handEdit;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getIntercept() {
        return intercept;
    }

    public void setIntercept(Integer intercept) {
        this.intercept = intercept;
    }

    public Integer getAftersales() {
        return aftersales;
    }

    public void setAftersales(Integer aftersales) {
        this.aftersales = aftersales;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Integer getOrderLock() {
        return orderLock;
    }

    public void setOrderLock(Integer orderLock) {
        this.orderLock = orderLock;
    }

    public String getLockUser() {
        return lockUser;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getMarkContent() {
        return markContent;
    }

    public void setMarkContent(String markContent) {
        this.markContent = markContent;
    }

    public String getSellerFlag() {
        return sellerFlag;
    }

    public void setSellerFlag(String sellerFlag) {
        this.sellerFlag = sellerFlag;
    }

    public Integer getSizeType() {
        return sizeType;
    }

    public void setSizeType(Integer sizeType) {
        this.sizeType = sizeType;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Date getSendoutTime() {
        return sendoutTime;
    }

    public void setSendoutTime(Date sendoutTime) {
        this.sendoutTime = sendoutTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

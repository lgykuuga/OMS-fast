package com.lgy.oms.domain.order;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细信息表 oms_order_detail
 *
 * @author lgy
 * @date 2019-10-22
 */
@TableName("oms_order_detail")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 订单编号
     */
    private String biid;

    /**
     * 来源编号
     */
    private String tid;

    /**
     * 行序号
     */
    private String rowNumber;

    /**
     * 来源行序号
     */
    private String sourceRow;

    /**
     * 商品编码
     */
    private String commodity;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 平台子订单编号
     */
    private String oid;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 商品类型
     */
    private Integer type;

    /**
     * 商品图片绝对路径
     */
    private String picPath;

    /**
     * 商品数字ID
     */
    private String numIid;

    /**
     * 商家外部编码
     */
    private String outerIid;

    /**
     * 平台skuID
     */
    private String skuId;

    /**
     * 外部网店自己定义的Sku编号
     */
    private String outerSkuId;

    /**
     * 销售单价
     */
    private BigDecimal price;

    /**
     * 应付金额
     */
    private BigDecimal totalFee;

    /**
     * 实付金额
     */
    private BigDecimal payment;

    /**
     * 分摊之后的实付金额
     */
    private BigDecimal divideOrderFee;

    /**
     * 尺寸
     */
    private String size;

    /**
     * 商品条码
     */
    private String barCode;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 活动编码
     */
    private String active;

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

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getSourceRow() {
        return sourceRow;
    }

    public void setSourceRow(String sourceRow) {
        this.sourceRow = sourceRow;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getNumIid() {
        return numIid;
    }

    public void setNumIid(String numIid) {
        this.numIid = numIid;
    }

    public String getOuterIid() {
        return outerIid;
    }

    public void setOuterIid(String outerIid) {
        this.outerIid = outerIid;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getDivideOrderFee() {
        return divideOrderFee;
    }

    public void setDivideOrderFee(BigDecimal divideOrderFee) {
        this.divideOrderFee = divideOrderFee;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

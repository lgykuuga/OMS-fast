package com.lgy.oms.domain.dto.ods.response.order;

/**
 * 订单明细
 *
 * @author admin
 */
public class SaleOrderLinesBean {

    /**
     * 单据行号，string（50）
     */
    private String orderLineNo;
    /**
     * 交易平台订单, string (50)
     */
    private String orderCode;
    /**
     * 交易平台子订单编码, string (50)
     */
    private String subOrderCode;
    /**
     * 平台Item编码, string (50) , 必填
     */
    private String itemCode;
    /**
     * 外部网店自己定义的Item编号, string (50) ,条件必填
     */
    private String outerItemId;
    /**
     * 平台SKU编码, string (50) , 必填
     */
    private String skuId;
    /**
     * 外部网店自己定义的Sku编号
     */
    private String outerSkuId;
    /**
     * 数量
     */
    private String num;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品价格
     */
    private String price;
    /**
     * 实际成交价
     */
    private String payment;
    /**
     * 优惠金额
     */
    private String discountPrice;
    /**
     * 应付金额
     */
    private String totalPrice;
    /**
     * 手工调整金额
     */
    private String adjustPrice;

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSubOrderCode() {
        return subOrderCode;
    }

    public void setSubOrderCode(String subOrderCode) {
        this.subOrderCode = subOrderCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOuterItemId() {
        return outerItemId;
    }

    public void setOuterItemId(String outerItemId) {
        this.outerItemId = outerItemId;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(String adjustPrice) {
        this.adjustPrice = adjustPrice;
    }
}

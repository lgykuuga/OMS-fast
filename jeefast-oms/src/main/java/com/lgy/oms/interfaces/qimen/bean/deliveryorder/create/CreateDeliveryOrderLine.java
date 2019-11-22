package com.lgy.oms.interfaces.qimen.bean.deliveryorder.create;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 单据列表
 *
 * @author LGy
 */
@XStreamAlias("orderLine")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateDeliveryOrderLine {
    /**
     * 单据行号
     */
    private String orderLineNo;
    /**
     * 交易平台订单
     */
    private String sourceOrderCode;
    /**
     * 交易平台子订单编码
     */
    private String subSourceOrderCode;
    /**
     * 支付平台交易号(淘系订单传支付宝交易号)
     */
    private String payNo;
    /**
     * 货主编码, string (50) , 必填
     */
    private String ownerCode;
    /**
     * 商品编码, string (50) , 必填
     */
    private String itemCode;
    /**
     * 仓储系统商品编码, string (50)
     */
    private String itemId;
    /**
     * 库存类型，string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存，默认为查所有类型的库存
     */
    private String inventoryType;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 交易平台商品编码
     */
    private String extCode;
    /**
     * 应发商品数量
     */
    private int planQty;

    /**
     * 零售价, double (18, 2) , 零售价=实际成交价+单件商品折扣金额
     */
    private String retailPrice;
    /**
     * 实际成交价,
     */
    private String actualPrice;
    /**
     * 单件商品折扣金额
     */
    private String discountAmount;

    /**
     * 批次编码
     */
    private String batchCode;

    /**
     * 商品生产日期(YYYY-MM-DD)
     */

    private String productDate;
    /**
     * 商品过期日期(YYYY-MM-DD)
     */

    private String expireDate;


    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public String getSourceOrderCode() {
        return sourceOrderCode;
    }

    public void setSourceOrderCode(String sourceOrderCode) {
        this.sourceOrderCode = sourceOrderCode;
    }

    public String getSubSourceOrderCode() {
        return subSourceOrderCode;
    }

    public void setSubSourceOrderCode(String subSourceOrderCode) {
        this.subSourceOrderCode = subSourceOrderCode;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getExtCode() {
        return extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }


}

package com.lgy.oms.domain.dto.qimen.inventory.report;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @Description 商品明细
 * @Author LGy
 * @Date 2019/11/18
 */
@XStreamAlias("item")
@XmlAccessorType(XmlAccessType.FIELD)
public class InventoryReportItem {

    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 仓储系统商品编码, string (50) , 条件必填, 条件为商品同步接口, 出参itemId不为空
     */
    private String itemId;
    /**
     * 库存类型，string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存，默认为ZP
     */
    private String inventoryType;
    /**
     * 数量
     */
    private String quantity;
    /**
     * 批次编码, string (50)
     */
    private String batchCode;
    /**
     * 商品生产日期 YYYY-MM-DD
     */
    private String productDate;
    /**
     * 商品过期日期YYYY-MM-DD
     */
    private String expireDate;
    /**
     * 生产批号, string (50)
     */
    private String produceCode;
    /**
     * 商品序列号
     */
    private String snCode;
    /**
     * 备注, string (500)
     */
    private String remark;
    /**
     * 库存商品总量
     */
    private String totalQty;

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

}

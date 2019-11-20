package com.lgy.oms.domain.dto.qimen.stockchange;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 批次信息
 *
 * @author LGy.
 */
@XStreamAlias("batch")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockChangeBatch {

    /**
     * 批次编号
     */
    private String batchCode;

    /**
     * 生产日期(YYYY-MM-DD)
     */
    private String productDate;

    /**
     * 过期日期(YYYY-MM-DD)
     */
    private String expireDate;

    /**
     * 生产批号
     */
    private String produceCode;

    /**
     * 库存类型(ZP=正品;CC=残次;JS=机损 XS= 箱损;ZT=在途库存)
     */
    private String inventoryType;

    /**
     * 异动数量(要求batchs节点下所有的异动数量之和等于orderline中的异动数量)
     */
    private Double quantity;

    /**
     * 实发数量
     */
    private Double actualQty;

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

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

}

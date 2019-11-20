package com.lgy.oms.domain.dto.qimen.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 发货确认接口批次
 *
 * @author LGy
 */
@XStreamAlias("batch")
public class ConfirmBatch {
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
     * 库存类型(ZP=正品;CC=残次;JS=机损;XS=箱损;ZT=在途库存;默认为查所有类型的库存)
     */
    private String inventoryType;
    /**
     * 实发数量(要求batchs节点下所有的实发数量之和等于orderline中的实发数量)
     */
    private Integer actualQty;

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

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

}

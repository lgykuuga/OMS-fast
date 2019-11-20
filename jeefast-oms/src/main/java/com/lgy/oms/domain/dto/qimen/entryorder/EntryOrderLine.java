package com.lgy.oms.domain.dto.qimen.entryorder;


import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 入库单明细
 *
 * @author LGy.
 */
@XStreamAlias("orderLine")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryOrderLine {

    /**
     * 外部业务编码(消息ID;用于去重;当单据需要分批次发送时使用)
     */
    private String outBizCode;
    /**
     * 单据行号
     */
    private String orderLineNo;
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 货主编码
     */
    private String ownerCode;
    /**
     * 仓储系统商品ID
     */
    private String itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 应收商品数量
     */
    private Long planQty;
    /**
     * 实收商品数量
     */
    private Long actualQty;
    /**
     * 商品属性
     */
    private String skuProperty;
    /**
     * 采购价
     */
    private String purchasePrice;
    /**
     * 零售价
     */
    private String retailPrice;
    /**
     * 库存类型，string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存，DJ=冻结库存，默认为ZP,
     */
    private String inventoryType;
    /**
     * 商品生产日期 YYYY-MM-DD
     */
    private String productDate;
    /**
     * 商品过期日期YYYY-MM-DD
     */
    private String expireDate;
    /**
     * 生产批号
     */
    private String produceCode;
    /**
     * 批次编码
     */
    private String batchCode;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity remark;

    /**
     * 批次信息
     */
    @XmlElementWrapper(name = "batchs")
    @XmlElement(name = "batch")
    private List<EntryBatch> batchs;

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public Long getActualQty() {
        return actualQty;
    }

    public void setActualQty(Long actualQty) {
        this.actualQty = actualQty;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
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

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public List<EntryBatch> getBatchs() {
        return batchs;
    }

    public void setBatchs(List<EntryBatch> batchs) {
        this.batchs = batchs;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }

    public ExtendPropsEntity getRemark() {
        return remark;
    }

    public void setRemark(ExtendPropsEntity remark) {
        this.remark = remark;
    }
}

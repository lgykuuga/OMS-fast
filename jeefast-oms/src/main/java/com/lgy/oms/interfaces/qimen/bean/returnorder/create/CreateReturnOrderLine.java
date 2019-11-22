package com.lgy.oms.interfaces.qimen.bean.returnorder.create;


import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;
import com.lgy.oms.interfaces.qimen.bean.returnorder.ReturnOrderBatch;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * 订单明细信息
 *
 * @author LGy.
 */
@XStreamAlias("orderLine")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateReturnOrderLine {

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
     * 库存类型, string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损, 默认为ZP
     */
    private String inventoryType;
    /**
     * 应收商品数量
     */
    private Long planQty;
    /**
     * 实收商品数量
     */
    private Long actualQty;
    /**
     * 批次编码, string (50)
     */
    private String batchCode;
    /**
     * 生产日期, string (10) , YYYY-MM-DD
     */
    private String productDate;
    /**
     * 过期日期, string (10) , YYYY-MM-DD
     */
    private String expireDate;
    /**
     * 生产批号, string (50)
     */
    private String produceCode;

    /**
     * 商品的二维码(类似电子产品的SN码;用来进行商品的溯源;多个二维码之间用分号;隔开)
     */
    private String qrCode;

    /**
     * 批次信息
     */
    private List<ReturnOrderBatch> batchs;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

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

    public Long getActualQty() {
        return actualQty;
    }

    public void setActualQty(Long actualQty) {
        this.actualQty = actualQty;
    }

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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
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

    public Long getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Long planQty) {
        this.planQty = planQty;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public List<ReturnOrderBatch> getBatchs() {
        return batchs;
    }

    public void setBatchs(List<ReturnOrderBatch> batchs) {
        this.batchs = batchs;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}

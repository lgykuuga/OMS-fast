package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * 批量确认接口 单据列表
 *
 * @author LGy
 */
@XStreamAlias("orderLine")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmDeliveryOrderLine {
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
    private String planQty;
    /**
     * 实发商品数量
     */
    private String actualQty;

    /**
     * 批次编码
     */
    private String batchCode;

    /**
     * 商品生产日期(YYYY-MM-DD)
     */

    private String productDate;
    /**
     * 生产批号
     */

    private String produceCode;
    /**
     * 商品过期日期(YYYY-MM-DD)
     */

    private String expireDate;

    /**
     * 批次列表
     */
    @XmlElementWrapper(name = "batchs")
    @XmlElement(name = "batch")
    private List<ConfirmBatch> batchs;

    /**
     * 商品的二维码(类似电子产品的SN码;用来进行商品的溯源;多个二维码之间用分号;隔开)
     */
    private String qrCode;
    /**
     * 仓库拆单子发货单号
     */
    private String subDeliveryOrderId;

    /**
     * snList
     */
    @XmlElementWrapper(name = "snList")
    @XmlElement(name = "sn")
    private List<String> snList;


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

    public String getPlanQty() {
        return planQty;
    }

    public void setPlanQty(String planQty) {
        this.planQty = planQty;
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

    public String getActualQty() {
        return actualQty;
    }

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }



    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSubDeliveryOrderId() {
        return subDeliveryOrderId;
    }

    public void setSubDeliveryOrderId(String subDeliveryOrderId) {
        this.subDeliveryOrderId = subDeliveryOrderId;
    }


}

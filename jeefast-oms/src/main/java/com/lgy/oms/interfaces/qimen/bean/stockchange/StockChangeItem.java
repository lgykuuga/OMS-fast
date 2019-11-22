package com.lgy.oms.interfaces.qimen.bean.stockchange;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 商品信息
 *
 * @author LGy
 */
@XStreamAlias("item")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockChangeItem {

    /**
     * 外部业务编码, 消息ID, 用于去重，用来保证因为网络等原因导致重复传输，请求不会被重复处理，必填
     */
    private String outBizCode;
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 仓储系统商品编码, string (50) , 条件必填, 条件为商品同步接口, 出参itemId不为空
     */
    private String itemId;
    /**
     * 库存类型，string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存
     */
    private String inventoryType;
    /**
     * 商品变化量
     */
    private Double quantity;
    /**
     * 批次编码
     */
    private String batchCode;
    /**
     * 商品生产日期
     */
    private String productDate;
    /**
     * 商品过期日期
     */
    private String expireDate;
    /**
     * 生产批号
     */
    private String produceCode;
    /**
     * 异动时间
     */
    private String changeTime;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 引起异动的单据编码
     */
    private String orderCode;
    /**
     * 单据类型 ，string（50），JYCK= 一般交易出库单，HHCK= 换货出库 ，BFCK= 补发出库 PTCK=普通出库单，DBCK=调拨出库 ，QTCK=其他出库， SCRK=生产入库，LYRK=领用入库，CCRK=残次品入库，CGRK=采购入库 ，
     * DBRK= 调拨入库 ，QTRK= 其他入库 ，XTRK= 销退入库 HHRK= 换货入库 CNJG= 仓内加工单
     */
    private String orderType;
    /**
     * 批次备注,
     */
    private String remark;

    /**
     * 货主编码
     */
    private String ownerCode;

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

}

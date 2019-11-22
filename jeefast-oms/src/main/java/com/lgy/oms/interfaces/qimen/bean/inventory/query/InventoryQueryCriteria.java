package com.lgy.oms.interfaces.qimen.bean.inventory.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 库存查询接口（多商品）
 *
 * @author LGy
 */
@XStreamAlias("criteria")
@XmlAccessorType(XmlAccessType.FIELD)
public class InventoryQueryCriteria {
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 仓储系统商品
     */
    private String itemId;
    /**
     * 库存类型，string (50) , ZP=正品, CC=残次,JS=机损, XS= 箱损, ZT=在途库存，默认为查所有类型的库存
     */
    private String inventoryType;

    /**
     * 货主编码
     */
    private String ownerCode;

    /**
     * 备注
     */
    private String remark;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
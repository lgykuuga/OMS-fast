package com.lgy.oms.domain.dto.qimen.items.synchronize;

import com.lgy.oms.domain.dto.qimen.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * taobao.qimen.singleitem.synchronize( 商品同步接口 )
 * ERP调用奇门的接口,同步商品信息给WMS
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlRootElement(name = "request")
public class ItemSynchronizeRequest {
    /**
     * add|update, 必填
     */
    private String actionType;
    /**
     * 仓库编码, string (50)
     */
    private String warehouseCode;
    /**
     * 货主编码
     */
    private String ownerCode;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 明细
     */
    private Item item;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }
}

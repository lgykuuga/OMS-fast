package com.lgy.oms.interfaces.qimen.bean.items.combineitem;

import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * taobao.qimen.combineitem.synchronize( 组合商品接口 )
 *
 * @author LGy
 */
@XStreamAlias("request")
public class CombineItemSyncRequest {
    /**
     * 组合商品的ERP编码
     */
    private String itemCode;
    /**
     * 仓库编码, string (50)
     */
    private String warehouseCode;
    /**
     * 货主编码
     */
    private String ownerCode;
    /**
     * 明细
     */
    private List<CombineItemItem> items;

    /**
     * 扩展属性
     */
    private ExtendPropsEntity extendProps;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public List<CombineItemItem> getItems() {
        return items;
    }

    public void setItems(List<CombineItemItem> items) {
        this.items = items;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }


}

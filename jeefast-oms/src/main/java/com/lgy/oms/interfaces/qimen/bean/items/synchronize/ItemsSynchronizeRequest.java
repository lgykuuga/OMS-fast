package com.lgy.oms.interfaces.qimen.bean.items.synchronize;

import com.lgy.oms.interfaces.qimen.bean.ExtendPropsEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * taobao.qimen.items.synchronize( 商品同步接口 (批量) )
 *
 * @author LGy
 */
@XStreamAlias("request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class ItemsSynchronizeRequest {
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
     * 明细
     */
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items;

    /**
     * 扩展属性
     */
    @XmlElement(name = "extendProps")
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public ExtendPropsEntity getExtendProps() {
        return extendProps;
    }

    public void setExtendProps(ExtendPropsEntity extendProps) {
        this.extendProps = extendProps;
    }


}

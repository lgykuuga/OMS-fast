package com.lgy.oms.interfaces.qimen.bean.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 包材信息
 *
 * @author LGy
 */
@XStreamAlias("packageMaterial")
public class ConfirmPackageMaterial {
    /**
     * 包材型号
     */
    private String type;
    /**
     * 包材的数量
     */
    private Integer quantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}

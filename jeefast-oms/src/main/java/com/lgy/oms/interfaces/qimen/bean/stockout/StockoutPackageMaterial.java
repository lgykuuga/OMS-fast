package com.lgy.oms.interfaces.qimen.bean.stockout;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 包材信息
 *
 * @author LGy
 */
@XStreamAlias("packageMaterial")
public class StockoutPackageMaterial {
    /**
     * 包材型号
     */
    private String type;
    /**
     * 包材的数量
     */
    private int quantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

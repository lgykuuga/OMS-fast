package com.lgy.oms.domain.dto.qimen.stockout;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 商品信息
 *
 * @author LGy
 */
@XStreamAlias("item")
public class StockoutItem {
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 仓储系统商品编码, string (50) , 条件必填, 条件为商品同步接口, 出参itemId不为空
     */
    private String itemId;
    /**
     * 商品数量
     */
    private Long quantity;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}

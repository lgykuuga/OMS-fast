package com.lgy.oms.interfaces.ods.bean.response.order;

/**
 * 订单发票明细信息
 *
 * @author admin
 */
public class InvoiceItemBean {
    /**
     * 商品名称, string (50)
     */
    private String itemName;
    /**
     * 商品单位, string (50)
     */
    private String unit;
    /**
     * 商品单价, double (18, 2)
     */
    private Double price;
    /**
     * 数量, int
     */
    private Integer quantity;
    /**
     * 金额, double (18, 2)
     */
    private Double amount;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

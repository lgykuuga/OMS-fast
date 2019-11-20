package com.lgy.oms.domain.dto.qimen.deliveryorder.confirm;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 发货确认:商品列表
 * @author LGy
 *
 */
@XStreamAlias("item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfirmItem {
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 商品单位
	 */
	private String unit;
	/**
	 * 商品单价
	 */
	private String price;
	/**
	 * 数量
	 */
	private Integer quantity;
	
	/**
	 * 金额
	 */
	private String  amount;
	/**
	 * 商品编码
	 */
	private String   itemCode;
	/**
	 * 商品仓储系统编码
	 */
	private String  itemId;
	
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	
}

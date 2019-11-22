package com.lgy.oms.interfaces.ods.bean.response.order;

/**
 * @Description 优惠信息
 * @Author LGy
 * @Date 2019/10/14
 */
public class CouponDetailBean {
	
	/** 订单编号 */
	private String orderId ;
	
	/** 京东sku编号 */
	private String skuId;
	
	/** 京东优惠类型: 20-套装优惠, 28-闪团优惠, 29-团购优惠, 30-单品促销优惠, 34-手机红包, 35-满返满送(返现), 39-京豆优惠,41-京东券优惠, 52-礼品卡优惠,100-店铺优惠  */
	private String couponType;
	
	/** 优惠金额 */
	private String couponPrice;
	
	/** 优惠信息的名称 */
	private String promotionName;
	
	/** 满就送商品时，所送商品的名称 */
	private String giftItemName;
	
	/** 赠品的宝贝id */
	private String giftItemId;
	
	/** 满就送礼物的礼物数量 */
	private String giftItemNum;
	
	/** 优惠活动的描述 */
	private String promotionDesc;
	
	/** 优惠id，(由营销工具id、优惠活动id和优惠详情id组成，结构为：营销工具id-优惠活动id_优惠详情id，如mjs-123024_211143） */
	private String promotionId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getGiftItemName() {
		return giftItemName;
	}

	public void setGiftItemName(String giftItemName) {
		this.giftItemName = giftItemName;
	}

	public String getGiftItemId() {
		return giftItemId;
	}

	public void setGiftItemId(String giftItemId) {
		this.giftItemId = giftItemId;
	}

	public String getGiftItemNum() {
		return giftItemNum;
	}

	public void setGiftItemNum(String giftItemNum) {
		this.giftItemNum = giftItemNum;
	}

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	
}

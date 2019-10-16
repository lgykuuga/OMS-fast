package com.lgy.oms.enums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 平台订单状态
 * @author LGy
 */
public enum PlatformOrderStatusEnum {
	
	/** 等待买家付款 */
	WAIT_BUYER_PAY(0),
	/** 等待卖家发货,即:买家已付款 */
	WAIT_SELLER_SEND_GOODS(5),
	/** 等待买家确认收货,即:卖家已发货 */
	WAIT_BUYER_CONFIRM_GOODS(10),
	/** 买家已签收,货到付款专用 */
	TRADE_BUYER_SIGNED(15),
	/** 交易成功 */
	TRADE_FINISHED(99),
	/** 付款以后用户退款成功，交易自动关闭 */
	TRADE_CLOSED(95);

	private final int value;

	PlatformOrderStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	/** 订单状态集合 */
	private static Map<Integer,String> platformOrderStatusMap;

	/** 获取订单状态集合 */
	public static Map<Integer, String> getPlatformOrderStatusMap() {
		if (platformOrderStatusMap == null) {
			platformOrderStatusMap = new HashMap<>(6);
			platformOrderStatusMap.put(0, "等待买家付款");
			platformOrderStatusMap.put(5,"卖家已付款");
			platformOrderStatusMap.put(10,"卖家已发货");
			platformOrderStatusMap.put(15, "货到付款已签收");
			platformOrderStatusMap.put(99, "交易成功");
			platformOrderStatusMap.put(95, "交易关闭");
		}
		return platformOrderStatusMap;
	}

	/**
	 * 将订单状态封装为json
	 * @return
	 */
	public static String toJson(){
		String JsOrderFlag = "[";
		Iterator<Map.Entry<Integer, String>> iterator = getPlatformOrderStatusMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Integer, String> entry = iterator.next();
			JsOrderFlag += "{\"key\":\""+entry.getKey()+"\",\"val\":\""+entry.getValue()+"\"},";
		}
		return JsOrderFlag = JsOrderFlag.substring(0, JsOrderFlag.length()-1)+"]";
	}


	/**
	 * 匹配转换
	 * @param status 状态值
	 * @return
	 */
	public static int switchStatus(String status){
		PlatformOrderStatusEnum[] values = PlatformOrderStatusEnum.values();
		for (PlatformOrderStatusEnum platformOrderStatusEnum : values) {
			if (platformOrderStatusEnum.name().equalsIgnoreCase(status)) {
				return platformOrderStatusEnum.value;
			}
		}
		return 100;
	}
}

package com.lgy.oms.constants;

/**
 * 订单操作类型
 * @author LGy
 */
public enum OrderOperateType {

	/**
	 * 操作类型,用于订单轨迹日志记录
	 */
	ADD("订单新增"),
	DOWNLOAD("订单下载"),
	CONVERT("订单转入"),
	ACCEPT("订单接收"),
	IMPORT("订单导入"),
	EDIT("订单编辑"),
	EDIT_DETAIL("订单明细编辑"),
	DELETE("订单删除"),
	CANCEL("订单取消"),
	LOCK("订单锁定"),
	UNLOCK("订单解锁"),
	FROZEN("订单冻结"),
	THAW("订单解冻"),
	COPY("订单复制"),
	FINISH("订单完成"),
	STATISTICS("订单统计"),
	ASSIGN("指派订单"),
	PUSH("推送订单"),
	PUSH_INTERCEPT("推送拦截"),
	
	SPLIT("订单拆分"),
	COMBINE("订单合并"),
	LOCK_STOCK("订单锁定库存"),
	UNLOCK_STOCK("订单解锁库存"),
	CLEAN_YC("订单清除异常"),
	
	WAREHOUSE_MATCH("订单分配仓库"),
	LOGISTICS_MATCH("订单分配物流商"),
	LOGISTICS_NUMBER_MATCH("订单分配物流单号"),

	INTERFACES_ORDER_UPDATE("平台订单状态更新"),
	SEND_OUT("订单发货完成"),
	UPLOAD_INTERFACES("订单回传平台"),
	UPLOAD_ERP("订单回传ERP"),
	
	AUDIT_CHECK("订单校验"),

	AUDIT_INTERCEPT("审核订单拦截"),
	AUDIT_SPECIAL_INTERCEPT("特定信息拦截"),
	AUDIT_CHECK_SAME_ORDER("校验相同来源单号"),
	BUYER_MESSAGE("买家留言拦截"),
	SELLER_MESSAGE("卖家留言拦截"),
	COD("货到付款拦截"),
	SELLER_FLAG("备注旗帜拦截"),
	AUDIT_DETAIL_INTERCEPT("订单明细拦截"),
	REFUND("退款拦截"),
	AMOUNT("校验金额拦截"),
	DATE("校验日期拦截"),
	NUMBER("校验数值拦截"),
	MATCH_MARKETING("订单匹配活动"),
	MATCH_SELLER_MSG("订单匹配卖家留言"),

	AUDIT("审核订单"),
	CREATE_DISTRIBUTION("生成配货单"),
	PUSH_TO_WMS("订单推送至仓库"),

	/**
	 * 检测到订单状态为非正常状态(例:在平台上检测到订单为已发货状态,
	 * 订单在系统是已取消状态),记录日志.
	 */
	ABNORMAL("订单异常"),
	SET_ABNORMAL("设置异常"),

	LOGISTICS_INFO("订单分配物流信息"),
	DISTRIBUTION("配货"),
	REFERENCE("关联售后单"),
	SET_REMARK("设置备注"),
	INVALID("作废售后单"),

	/** ====== 策略动作 =========*/
	RULE_ADD("新增策略"),
	RULE_EDIT("修改策略"),
	RULE_DEL("删除策略"),
	RULE_COPY("复制策略"),
	RULE_OPEN("开启策略"),
	RULE_CLOSE("关闭策略");
	
	private String value;

	OrderOperateType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

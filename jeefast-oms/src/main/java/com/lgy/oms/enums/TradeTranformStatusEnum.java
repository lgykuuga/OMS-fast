package com.lgy.oms.enums;

/**
 * 平台订单转单状态
 *
 * @author LGy
 */
public enum TradeTranformStatusEnum {

    /**
     * 未转单
     */
    WAIT_TRANFORM(0),
    /**
     * 已转单
     */
    TRANFORM(1),
    /**
     * 已取消
     */
    CANCEL(2);

    private int value;

    TradeTranformStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static String toJson() {
        String JsFlag = "[";
        for (TradeTranformStatusEnum trade : TradeTranformStatusEnum.values()) {
            JsFlag += "{\"key\":\"" + trade.name() + "\",\"val\":\"" + trade.value + "\"},";
        }
        return JsFlag = JsFlag.substring(0, JsFlag.length() - 1) + "]";
    }

}

package com.lgy.oms.enums;

/**
 * @Description 下单策略枚举
 * @Author LGy
 * @Date 2019/10/14
 */
public enum DownloadOrderStrategyEnum {

    /**
     * 用户自定义
     */
    USERDEFINED("用户自定义"),
    /**
     * 标准模式
     */
    STANDARD("标准模式"),
    /**
     * 大促模式
     */
    PROMOTIONAL("大促模式");

    private String name;

    DownloadOrderStrategyEnum(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String toJson() {
        String JsOrderFlag = "[";
        for (DownloadOrderStrategyEnum downloadOrderStrategyEnum : DownloadOrderStrategyEnum.values()) {
            JsOrderFlag += "{\"key\":\""+downloadOrderStrategyEnum.name()+"\",\"val\":\""+downloadOrderStrategyEnum.getName()+"\"},";
        }
        return JsOrderFlag = JsOrderFlag.substring(0, JsOrderFlag.length()-1)+"]";
    }
}

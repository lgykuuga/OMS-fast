package com.lgy.oms.enums;

import com.lgy.system.domain.SysConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 下单策略接口类型枚举(对接订单下载系统)
 * @Author LGy
 * @Date 2019/10/17
 */
public enum DownloadOrderInterfaceEnum {

    /**
     * 直接对接平台
     */
    DIRECT("对接平台"),
    /**
     * globale:跨境翼
     */
    KJY("跨境翼"),
    /**
     * gwall: ODS
     */
    GODS("GODS");


    private String value;

    /**
     * 订单状态集合
     */
    private static List<SysConfig> configs;

    public String getValue() {
        return value;
    }

    DownloadOrderInterfaceEnum(String value) {
        this.value = value;
    }

    public static String toJson() {
        String JsOrderFlag = "[";
        for (DownloadOrderInterfaceEnum downloadOrderStrategyEnum : DownloadOrderInterfaceEnum.values()) {
            JsOrderFlag += "{\"key\":\"" + downloadOrderStrategyEnum.name() + "\",\"val\":\"" + downloadOrderStrategyEnum.getValue() + "\"},";
        }
        return JsOrderFlag = JsOrderFlag.substring(0, JsOrderFlag.length() - 1) + "]";
    }

    /**
     * 获取订单状态集合
     */
    public final static List<SysConfig> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (DownloadOrderInterfaceEnum strategyEnum : DownloadOrderInterfaceEnum.values()) {
                configs.add(new SysConfig(strategyEnum.name(), strategyEnum.getValue()));
            }
        }
        return configs;
    }

}
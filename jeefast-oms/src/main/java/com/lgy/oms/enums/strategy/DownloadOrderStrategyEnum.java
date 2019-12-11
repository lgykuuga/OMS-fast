package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 下单策略枚举
 * @Author LGy
 * @Date 2019/10/14
 */
public enum DownloadOrderStrategyEnum {

    /**
     * 用户自定义
     */
    DEFINED("用户自定义"),
    /**
     * 标准模式
     */
    STANDARD("标准模式"),
    /**
     * 大促模式
     */
    PROMOTIONAL("大促模式");

    private String value;

    /**
     * 策略集合
     */
    private static List<Config> configs;

    public String getValue() {
        return value;
    }

    DownloadOrderStrategyEnum(String value) {
        this.value = value;
    }

    public static String toJson() {
        String JsOrderFlag = "[";
        for (DownloadOrderStrategyEnum downloadOrderStrategyEnum : DownloadOrderStrategyEnum.values()) {
            JsOrderFlag += "{\"key\":\"" + downloadOrderStrategyEnum.name() + "\",\"val\":\"" + downloadOrderStrategyEnum.getValue() + "\"},";
        }
        return JsOrderFlag.substring(0, JsOrderFlag.length() - 1) + "]";
    }

    /**
     * 获取策略状态集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (DownloadOrderStrategyEnum strategyEnum : DownloadOrderStrategyEnum.values()) {
                configs.add(new Config(strategyEnum.name(), strategyEnum.getValue()));
            }
        }
        return configs;
    }

}

package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 转单策略触发节点
 * @Author LGy
 * @Date 2019/11/1 17:01
 **/
public enum ConvertTriggerNodeEnum {

    /**
     * 等待卖家发货节点
     */
    EQ_WAIT_SELLER_SEND_GOODS("0", "等待卖家发货"),
    /**
     * 小于等于等待卖家发货节点
     * (等待买家付款、等待卖家发货)
     */
    LE_WAIT_SELLER_SEND_GOODS("1", "等待买家付款、等待卖家发货"),
    /**
     * 有效订单
     */
    ALL_VALID_ORDER("2", "有效订单"),

    /**
     * 所有订单(包含取消订单)
     */
    ALL_ORDER("3", "所有订单");

    private final String key;
    private final String value;

    /**
     * 节点集合
     */
    private static List<Config> configs;

    /**
     * 获取节点集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (ConvertTriggerNodeEnum nodeEnum : ConvertTriggerNodeEnum.values()) {
                configs.add(new Config(nodeEnum.getKey(), nodeEnum.getValue()));
            }
        }
        return configs;
    }

    ConvertTriggerNodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String toJson() {
        String json = "[";
        for (ConvertTriggerNodeEnum nodeEnum : ConvertTriggerNodeEnum.values()) {
            json += "{\"key\":\"" + nodeEnum.getKey() + "\",\"val\":\"" + nodeEnum.getValue() + "\"},";
        }
        return json.substring(0, json.length() - 1) + "]";
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

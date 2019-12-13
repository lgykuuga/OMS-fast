package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台订单转单状态
 *
 * @author LGy
 */
public enum TradeTranformStatusEnum {

    /**
     * 未转单
     */
    WAIT_TRANFORM(0, "未转单"),
    /**
     * 已转单
     */
    TRANFORM(1, "已转单");


    private final int value;
    private final String name;

    /**
     * 订单状态集合
     */
    private static List<Config> configs;

    TradeTranformStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取策略状态集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (TradeTranformStatusEnum statusEnum : TradeTranformStatusEnum.values()) {
                configs.add(new Config(statusEnum.getValue() + "", statusEnum.getName()));
            }
        }
        return configs;
    }

}

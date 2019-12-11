package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 仓库接口类型
 * @Author LGy
 * @Date 2019/11/25
 */
public enum WarehouseInterfaceTypeEnum {

    /**
     * 奇门接口
     */
    QIMEN("奇门");


    private String value;

    /**
     * 订单状态集合
     */
    private static List<Config> configs;

    public String getValue() {
        return value;
    }

    WarehouseInterfaceTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 获取订单状态集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (WarehouseInterfaceTypeEnum strategyEnum : WarehouseInterfaceTypeEnum.values()) {
                configs.add(new Config(strategyEnum.name(), strategyEnum.getValue()));
            }
        }
        return configs;
    }

}

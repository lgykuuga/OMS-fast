package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单配货模式
 * @Author LGy
 * @Date 2020/2/2
 */
public enum DistributionModelEnum {

    /**
     * 标准模式
     */
    NORMAL(0, "标准模式"),

    /**
     * 定制流程-1
     */
    COMMISSION_1(1, "定制流程-1"),

    COMMISSION_2(2, "定制流程-2"),
    COMMISSION_3(3, "定制流程-3"),


    ;

    private Integer code;
    private String name;

    DistributionModelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 策略集合
     */
    private static List<Config> configs;


    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>(DistributionModelEnum.values().length);
            for (DistributionModelEnum strategyEnum : DistributionModelEnum.values()) {
                configs.add(new Config(strategyEnum.getCode().toString(), strategyEnum.getName()));
            }
        }
        return configs;
    }
}

package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单配货锁库方式
 * @Author LGy
 * @Date 2020/2/2
 */
public enum DistributionLockModelEnum {

    /**
     * 正常锁库
     */
    NORMAL(0, "正常锁库"),

    /**
     * 强制锁库
     */
    FORCE(1, "强制锁库");

    private Integer code;
    private String name;

    DistributionLockModelEnum(Integer code, String name) {
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
            configs = new ArrayList<>(DistributionLockModelEnum.values().length);
            for (DistributionLockModelEnum strategyEnum : DistributionLockModelEnum.values()) {
                configs.add(new Config(strategyEnum.getCode().toString(), strategyEnum.getName()));
            }
        }
        return configs;
    }
}

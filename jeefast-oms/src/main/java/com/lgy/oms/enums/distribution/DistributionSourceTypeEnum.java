package com.lgy.oms.enums.distribution;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 配货单来源类型
 * @Author LGy
 * @Date 2019/12/10
 */
public enum DistributionSourceTypeEnum {

    /**
     * 普通订单
     */
    NORMAL(0, "普通订单"),

    /**
     * 拆分
     */
    SPLIT(7, "拆分"),

    /**
     * 合并
     */
    COMBINE(8, "合并");

    private Integer code;
    private String name;

    DistributionSourceTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    private static List<Config> configs;


    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>(DistributionSourceTypeEnum.values().length);
            for (DistributionSourceTypeEnum typeEnum : DistributionSourceTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

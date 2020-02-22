package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单占用(锁库)类型
 * @Author LGy
 * @Date 2020/2/22
 */
public enum OrderLockStockEnum {

    /**
     * 未占用
     */
    NONE(0, "不占用"),

    /**
     * 部分占用
     */
    PART_LOCK(1, "部分占用"),

    /**
     * 完全占用
     */
    COMPLETE_LOCK(2, "完全占用");

    private Integer code;
    private String name;

    OrderLockStockEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderLockStockEnum.values().length);
            for (OrderLockStockEnum typeEnum : OrderLockStockEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

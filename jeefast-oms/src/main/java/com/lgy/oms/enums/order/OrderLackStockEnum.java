package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单缺货类型
 * @Author LGy
 * @Date 2020/2/22
 */
public enum OrderLackStockEnum {

    /**
     * 不缺货
     */
    NONE(0, "不缺货"),

    /**
     * 部分缺货
     */
    PART_LACK(1, "部分缺货"),

    /**
     * 完全缺货
     */
    COMPLETE_LACK(2, "完全缺货");

    private Integer code;
    private String name;

    OrderLackStockEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderLackStockEnum.values().length);
            for (OrderLackStockEnum typeEnum : OrderLackStockEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

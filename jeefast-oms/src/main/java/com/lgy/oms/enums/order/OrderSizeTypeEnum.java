package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单数量类型
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderSizeTypeEnum {

    /**
     * 一单一货
     */
    SINGLE_CARGO(1, "一单一货"),
    /**
     * 一单多货
     */
    MULTIPLE_CARGO(2, "一单多货"),
    /**
     * 大单
     */
    BIG_CARGO(3, "大单");

    /**
     * 定义大单数量
     */
    public static final int QTY = 30;

    private Integer code;
    private String name;

    OrderSizeTypeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderSizeTypeEnum.values().length);
            for (OrderSizeTypeEnum typeEnum : OrderSizeTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

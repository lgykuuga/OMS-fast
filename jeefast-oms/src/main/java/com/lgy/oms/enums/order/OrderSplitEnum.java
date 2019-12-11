package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单拆分状态
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderSplitEnum {

    /**
     * 等待拆分状态
     */
    WAIT(0, "等待拆分"),

    /**
     * 不拆分
     */
    NOT_SPLIT(1, "不拆分"),

    /**
     * 已拆分
     */
    SPLIT(2, "已拆分");

    private Integer code;
    private String name;

    OrderSplitEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderSplitEnum.values().length);
            for (OrderSplitEnum typeEnum : OrderSplitEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

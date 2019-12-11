package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单发货级别
 * @Author LGy
 * @Date 2019/12/10
 */
public enum OrderSendOutLevelEnum {

    /**
     * 普通任务
     */
    GENERAL(0, "普通任务"),

    /**
     * 紧急任务
     */
    COMMONLY(1, "紧急任务"),

    /**
     * 特急任务
     */
    SPECIAL(10, "特急任务");

    private Integer code;
    private String name;

    OrderSendOutLevelEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderSendOutLevelEnum.values().length);
            for (OrderSendOutLevelEnum typeEnum : OrderSendOutLevelEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

package com.lgy.oms.enums;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 接口推送报文格式类型
 * @Author LGy
 * @Date 2019/11/25
 */
public enum PushFormatEnum {

    /**
     * XML
     */
    XML("xml"),
    /**
     * JSON
     */
    JSON("json");


    private String value;

    /**
     * 订单状态集合
     */
    private static List<Config> configs;

    public String getValue() {
        return value;
    }

    PushFormatEnum(String value) {
        this.value = value;
    }

    /**
     * 获取订单状态集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (PushFormatEnum strategyEnum : PushFormatEnum.values()) {
                configs.add(new Config(strategyEnum.name(), strategyEnum.getValue()));
            }
        }
        return configs;
    }

}

package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单发货类型
 * @Author LGy
 * @Date 2019/12/10
 */
public enum OrderSendOutTypeEnum {

    /** 正常发货 */
    NORMAL_DELIVER(1, "正常发货"),
    /** 平台发货 */
    PLATFORM_DELIVER(2, "平台发货"),
    /** 刷单发货 */
    SD1(4, "刷单发货"),
    /** 刷单不发货 */
    SD2(5, "刷单不发货");

    private Integer code;
    private String name;

    OrderSendOutTypeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderSendOutTypeEnum.values().length);
            for (OrderSendOutTypeEnum typeEnum : OrderSendOutTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

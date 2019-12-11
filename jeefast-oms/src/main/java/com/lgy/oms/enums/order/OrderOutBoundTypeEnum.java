package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单出库类型
 * @Author LGy
 * @Date 2019/12/10
 */
public enum OrderOutBoundTypeEnum {

    /** 一般交易出库 */
    JYCK(1, "一般交易出库"),
    /** 换货出库 */
    HHCK(2, "换货出库"),
    /** 补发出库 */
    BFCK(3, "补发出库"),
    /** 其他出库单 */
    QTCK(4, "其他出库单");

    private Integer code;
    private String name;

    OrderOutBoundTypeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderOutBoundTypeEnum.values().length);
            for (OrderOutBoundTypeEnum typeEnum : OrderOutBoundTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

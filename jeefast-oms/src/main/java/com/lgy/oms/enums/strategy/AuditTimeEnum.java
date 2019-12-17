package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审单策略:时间范围拦截类型 && 有效期日期类型 && 自动审核时间类型
 * @Author LGy
 * @Date 2019/12/16
 */
public enum AuditTimeEnum {

    /**
     * 无操作
     */
    NOT_OPERATION(0, "无操作"),

    /**
     * 订单下单时间
     */
    ORDER_PLACEMENT(1, "订单下单时间"),

    /**
     * 订单支付时间
     */
    ORDER_PAY(2, "订单支付时间"),
    /**
     * 订单创建时间
     */
    ORDER_CREATE(3, "订单创建时间");

    private Integer code;
    private String name;

    AuditTimeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(AuditTimeEnum.values().length);
            for (AuditTimeEnum typeEnum : AuditTimeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

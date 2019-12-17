package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审单策略:数值拦截类型
 * @Author LGy
 * @Date 2019/12/16
 */
public enum AuditNumberEnum {

    /**
     * 不拦截
     */
    NOT_INTERCEPT(0, "不拦截"),

    /**
     * 订单商品总数量
     */
    ORDER_QTY(1, "订单商品数量"),

    /**
     * 订单重量
     */
    ORDER_WEIGHT(2, "订单重量"),

    /**
     * 订单体积
     */
    ORDER_VOLUME(3, "订单体积");

    private Integer code;
    private String name;

    AuditNumberEnum(Integer code, String name) {
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
            configs = new ArrayList<>(AuditNumberEnum.values().length);
            for (AuditNumberEnum typeEnum : AuditNumberEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

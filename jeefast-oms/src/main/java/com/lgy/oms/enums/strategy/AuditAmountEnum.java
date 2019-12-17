package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审单策略:金额拦截类型
 * @Author LGy
 * @Date 2019/12/16
 */
public enum AuditAmountEnum {

    /**
     * 不拦截
     */
    NOT_INTERCEPT(0, "不拦截"),

    /**
     * 订单金额
     */
    ORDER_AMOUNT(1, "订单金额"),

    /**
     * 支付金额
     */
    PAY_AMOUNT(2, "支付金额");

    private Integer code;
    private String name;

    AuditAmountEnum(Integer code, String name) {
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
            configs = new ArrayList<>(AuditAmountEnum.values().length);
            for (AuditAmountEnum typeEnum : AuditAmountEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

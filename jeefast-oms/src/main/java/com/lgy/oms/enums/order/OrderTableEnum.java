package com.lgy.oms.enums.order;

import com.lgy.oms.util.OrderFieldUtils;
import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单对象表枚举类型
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderTableEnum {

    /**
     * 订单主信息
     */
    MAIN(1, "订单主信息"),


    /**
     * 订单买家信息
     */
    BUYER_INFO(2, "订单买家信息"),

    /**
     * 订单支付信息
     */
    PAY_INFO(3, "订单支付信息"),

    /**
     * 订单状态信息
     */
    STATUS_INFO(4, "订单状态信息"),

    /**
     * 订单类型信息
     */
    TYPE_INFO(5, "订单类型信息"),

    /**
     * 订单拦截信息
     */
    INTERCEPT_INFO(6, "订单拦截信息"),

    /**
     * 订单明细信息
     */
    DETAIL(7, "订单明细信息"),

    ;

    private Integer code;
    private String name;

    OrderTableEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderTableEnum.values().length);
            for (OrderTableEnum typeEnum : OrderTableEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

    public static List<Config> getSelected(Integer code) {
        if (OrderTableEnum.MAIN.getCode().equals(code)) {
            return OrderFieldUtils.getOrderMainList();
        }
        if (OrderTableEnum.BUYER_INFO.getCode().equals(code)) {
            return OrderFieldUtils.getBuyerInfoList();
        }
        if (OrderTableEnum.PAY_INFO.getCode().equals(code)) {
            return OrderFieldUtils.getPayInfoList();
        }
        if (OrderTableEnum.STATUS_INFO.getCode().equals(code)) {
            return OrderFieldUtils.getStatusInfoList();
        }
        if (OrderTableEnum.TYPE_INFO.getCode().equals(code)) {
            return OrderFieldUtils.getTypeInfoList();
        }
        if (OrderTableEnum.INTERCEPT_INFO.getCode().equals(code)) {
            return OrderFieldUtils.getInterceptList();
        }
        if (OrderTableEnum.DETAIL.getCode().equals(code)) {
            return OrderFieldUtils.getDetailList();
        }
        return null;
    }


}

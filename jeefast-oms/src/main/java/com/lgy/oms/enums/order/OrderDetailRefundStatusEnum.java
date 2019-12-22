package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单明细退款状态
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderDetailRefundStatusEnum {


    /**
     * 无申请退款
     */
    NO_REFUND(0, "无申请退款"),

    /**
     * 买家已经申请退款，等待卖家同意
     */
    WAIT_SELLER_AGREE(1, "买家已经申请退款，等待卖家同意"),
    /**
     * 卖家已经同意退款，等待买家退货
     */
    WAIT_BUYER_RETURN_GOODS(2, "卖家已经同意退款，等待买家退货"),
    /**
     * 买家已经退货，等待卖家确认收货
     */
    WAIT_SELLER_CONFIRM_GOODS(3, "买家已经退货，等待卖家确认收货"),
    /**
     * 卖家拒绝退款
     */
    SELLER_REFUSE_BUYER(4, "卖家拒绝退款"),
    /**
     * 退款成功
     */
    SUCCESS(5, "退款成功");
    

    private Integer code;
    private String name;

    OrderDetailRefundStatusEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderDetailRefundStatusEnum.values().length);
            for (OrderDetailRefundStatusEnum typeEnum : OrderDetailRefundStatusEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

    /**
     * 枚举类型转换为编码
     * @param name
     * @return
     */
    public static Integer convert(String name) {
        for (OrderDetailRefundStatusEnum typeEnum : OrderDetailRefundStatusEnum.values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum.getCode();
            }
        }
        return OrderDetailRefundStatusEnum.NO_REFUND.getCode();
    }

}

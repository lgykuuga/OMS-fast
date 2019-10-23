package com.lgy.oms.enums;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台订单状态
 *
 * @author LGy
 */
public enum PlatformOrderStatusEnum {

    /**
     * 等待买家付款
     */
    WAIT_BUYER_PAY(0, "等待买家付款"),
    /**
     * 等待卖家发货,即:买家已付款
     */
    WAIT_SELLER_SEND_GOODS(5, "等待卖家发货"),
    /**
     * 等待买家确认收货,即:卖家已发货
     */
    WAIT_BUYER_CONFIRM_GOODS(10, "卖家已发货"),
    /**
     * 买家已签收,货到付款专用
     */
    TRADE_BUYER_SIGNED(15, "货到付款已签收"),
    /**
     * 交易成功
     */
    TRADE_FINISHED(90, "交易成功"),
    /**
     * 付款以后用户退款成功，交易自动关闭
     */
    TRADE_CLOSED(95, "交易关闭");

    private final int value;
    private final String name;

    /**
     * 订单状态集合
     */
    private static List<Config> configs;

    PlatformOrderStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /**
     * 匹配转换
     *
     * @param status 状态值
     * @return
     */
    public static int switchStatus(String status) {
        PlatformOrderStatusEnum[] values = PlatformOrderStatusEnum.values();
        for (PlatformOrderStatusEnum platformOrderStatusEnum : values) {
            if (platformOrderStatusEnum.name().equalsIgnoreCase(status)) {
                return platformOrderStatusEnum.value;
            }
        }
        return 100;
    }

    /**
     * 获取策略状态集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>();
            for (PlatformOrderStatusEnum statusEnum : PlatformOrderStatusEnum.values()) {
                configs.add(new Config(statusEnum.getValue() + "", statusEnum.getName()));
            }
        }
        return configs;
    }
}

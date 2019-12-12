package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单拦截类型
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderInterceptTypeEnum {

    /**
     * 无拦截
     */
    NONE(0, "无拦截"),

    /**
     * 匹配商品编码异常拦截
     */
    MATCH_GOODS_CODE(1, "匹配商品编码拦截"),

    /**
     * 解析组合商品异常
     */
    ANALYSIS_COMBO_COMMODITY(2, "解析组合商品异常"),

    /**
     * 来源单号相同拦截
     */
    SAME_RESOURCE(3, "来源单号相同拦截"),

    /**
     * 存在取消请求拦截
     */
    EXIST_CANCEL(4, "存在取消请求拦截"),

    /**
     * 特殊地址拦截
     */
    SPECIAL_ADDRESS(5, "特殊地址拦截"),

    /**
     * 特定信息拦截
     */
    SPECIAL_INFO(6, "特定信息拦截"),

    /**
     * 指定商品拦截
     */
    SPECIAL_COMMODITY(7, "指定商品拦截"),

    /**
     * 金额额度拦截
     */
    AMOUNT(8, "金额额度范围拦截"),

    /**
     * 重量范围拦截
     */
    WEIGHT(9, "重量范围拦截"),

    /**
     * 货到付款拦截
     */
    COD(10, "货到付款拦截"),

    /**
     * 卖家备注旗帜拦截
     */
    SELLER_FLAG(11, "卖家备注旗帜拦截"),

    /**
     * 买家留言拦截
     */
    BUYER_MESSAGE(12, "买家留言拦截"),

    /**
     * 卖家留言拦截
     */
    SELLER_MESSAGE(13, "卖家留言拦截"),

    /**
     * 退款拦截
     */
    REFUND(14, "退款拦截"),

    /**
     * 客服标记拦截
     */
    CUSTOMER(15, "客服标记拦截"),

    /**
     * 订单标记拦截
     */
    SIGN(16, "订单标记拦截"),

    /**
     * 生成配货单失败
     */
    CREATE_DISTRIBUTION(50, "生成配货单失败"),

    /**
     * 系统异常(try-catch)
     */
    SYSTEM_ERROR(99, "系统异常");


    private Integer code;
    private String name;

    OrderInterceptTypeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderInterceptTypeEnum.values().length);
            for (OrderInterceptTypeEnum typeEnum : OrderInterceptTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

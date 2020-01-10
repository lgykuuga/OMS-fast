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
     * 订单有效性校验拦截
     */
    VALIDITY_CHECK(1, "订单有效性校验拦截"),

    /**
     * 匹配商品编码异常拦截
     */
    MATCH_GOODS_CODE(2, "匹配商品编码拦截"),

    /**
     * 解析组合商品异常
     */
    ANALYSIS_COMBO_COMMODITY(3, "解析组合商品异常"),

    /**
     * 来源单号相同拦截
     */
    SAME_RESOURCE(4, "来源单号相同拦截"),

    /**
     * 存在取消请求拦截
     */
    EXIST_CANCEL(5, "存在取消请求拦截"),

    /**
     * 特殊地址拦截
     */
    SPECIAL_ADDRESS(6, "特殊地址拦截"),

    /**
     * 特定信息拦截
     */
    SPECIAL_INFO(7, "特定信息拦截"),

    /**
     * 指定商品拦截
     */
    SPECIAL_COMMODITY(8, "指定商品拦截"),

    /**
     * 金额额度拦截
     */
    AMOUNT(9, "金额额度范围拦截"),

    /**
     * 数值范围拦截
     */
    NUMBER(10, "数值范围拦截"),

    /**
     * 货到付款拦截
     */
    COD(11, "货到付款拦截"),

    /**
     * 卖家备注旗帜拦截
     */
    SELLER_FLAG(12, "卖家备注旗帜拦截"),

    /**
     * 买家留言拦截
     */
    BUYER_MESSAGE(13, "买家留言拦截"),

    /**
     * 卖家留言拦截
     */
    SELLER_MESSAGE(14, "卖家留言拦截"),

    /**
     * 退款拦截
     */
    REFUND(15, "退款拦截"),

    /**
     * 客服标记拦截
     */
    CUSTOMER(16, "客服标记拦截"),

    /**
     * 订单标记拦截
     */
    SIGN(17, "订单标记拦截"),

    /**
     * 订单日期拦截
     */
    DATE(18, "订单日期拦截"),

    /**
     * 生成配货单失败
     */
    CREATE_DISTRIBUTION(25, "生成配货单失败"),

    /**
     * 组合信息拦截
     */
    COMBINATION(50, "组合信息拦截"),

    /**
     * 多条拦截信息
     */
    MULTIPLE(75, "多条拦截信息"),

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

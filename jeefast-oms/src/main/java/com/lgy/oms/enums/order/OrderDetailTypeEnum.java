package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单明细类型
 * @Author LGy
 * @Date 2019/12/10
 */
public enum OrderDetailTypeEnum {

    /**
     * 未解析商品
     */
    UNPARSED(0, "未解析商品"),
    /**
     * 普通商品
     */
    DEFAULT(1, "普通商品"),
    /**
     * 组合商品
     */
    COMB(2, "组合商品"),
    /**
     * 组合明细商品
     */
    COMB_DETAIL(3, "组合商品明细"),
    /**
     * 系统赠品
     */
    SYSTEM_GIFT(4, "系统赠品"),
    /**
     * 平台赠品
     */
    PLATFORM_GIFT(5, "平台赠品"),
    /**
     * 耗材
     */
    CONSUMABLES(6, "耗材");

    private Integer code;
    private String name;

    OrderDetailTypeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderDetailTypeEnum.values().length);
            for (OrderDetailTypeEnum typeEnum : OrderDetailTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode()+"", typeEnum.getName()));
            }
        }
        return configs;
    }

}

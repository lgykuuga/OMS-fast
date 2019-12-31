package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审单策略:特定商品拦截
 * @Author LGy
 * @Date 2019/12/31
 */
public enum AuditCommodityEnum {

    /**
     * 商品编码
     */
    COMMODITY(0, "商品编码"),

    /**
     * 商品名称
     */
    TITLE(1, "商品名称"),

    /**
     * 商品品牌
     */
    BRAND(2, "商品品牌"),

    /**
     * 商品类别
     */
    CATEGORY(3, "商品类别");

    private Integer code;
    private String name;

    AuditCommodityEnum(Integer code, String name) {
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
            configs = new ArrayList<>(AuditCommodityEnum.values().length);
            for (AuditCommodityEnum typeEnum : AuditCommodityEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

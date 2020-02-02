package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审单策略:特定信息拦截类型
 * @Author LGy
 * @Date 2019/12/31
 */
public enum AuditSpecialEnum {

    /**
     * 买家ID
     */
    BUYER_ID(0, "买家ID"),

    /**
     * 买家姓名
     */
    BUYER_NAME(1, "买家姓名"),

    /**
     * 买家电话
     */
    BUYER_PHONE(2, "买家电话"),

    /**
     * 地址
     */
    ADDRESS(3, "地址");

    private Integer code;
    private String name;

    AuditSpecialEnum(Integer code, String name) {
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
            configs = new ArrayList<>(AuditSpecialEnum.values().length);
            for (AuditSpecialEnum typeEnum : AuditSpecialEnum.values()) {
                configs.add(new Config(typeEnum.getCode().toString(), typeEnum.getName()));
            }
        }
        return configs;
    }

}

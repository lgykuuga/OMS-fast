package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;
import com.sun.org.apache.xpath.internal.operations.Equals;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 判断条件枚举类型
 * @Author LGy
 * @Date 2019/12/16
 */
public enum ConditionEnum {

    /**
     * 等于
     */
    EQUAL("EQ", "等于"),

    /**
     * 不等于
     */
    NOT_EQUAL("NE", "不等于"),

    /**
     * 大于
     */
    GREATER_THAN("GT", "大于"),

    /**
     * 小于
     */
    LESS_THAN("LT", "小于"),

     /**
     * 大于等于
     */
     GREATER_THAN_OR_EQUAL("GE", "大于等于"),

    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL("LE", "小于等于"),

    /**
     * 两数之间
     */
    BETWEEN("BETWEEN", "两数之间(between)"),

    /**
     * 包含
     */
    CONTAINS("CONTAINS", "包含(contains、like)"),

    /**
     * 在数组数据当中
     */
    IN("IN", "在数组数据当中(in)"),

    ;

    private String code;
    private String name;

    ConditionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    private static List<Config> configs;


    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>(ConditionEnum.values().length);
            for (ConditionEnum typeEnum : ConditionEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单流程状态
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderFlagEnum {

    /**
     * 新增
     */
    NEW(1, "新增"),


    /**
     * 编辑中
     */
    EDIT(2, "编辑中"),

    /**
     * 已审核
     */
    CONFIRM(4, "已审核"),

    /**
     * 部分配货
     */
    PART_DISTRIBUTION(8, "部分配货"),

    /**
     * 已配货
     */
    DISTRIBUTION(9, "已配货"),

    /**
     * 部分推送
     */
    PART_PUSH(18, "部分推送"),

    /**
     * 已推送
     */
    PUSH(19, "已推送"),

    /**
     * 部分发货
     */
    PART_SENDOUT(28, "部分发货"),

    /**
     * 已发货
     */
    SENDOUT(29, "已发货"),

    /**
     * 已完成
     */
    COMPLETED(90, "已完成"),

    /**
     * 已取消
     */
    CLOSED(95, "已取消"),

    /**
     * 被合并状态
     */
    COMBINE(96, "被合并");

    private Integer code;
    private String name;

    OrderFlagEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderFlagEnum.values().length);
            for (OrderFlagEnum typeEnum : OrderFlagEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

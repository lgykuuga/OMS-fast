package com.lgy.oms.enums.distribution;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单流程状态
 * @Author LGy
 * @Date 2019/12/11
 */
public enum DistributionFlagEnum {

    /**
     * 等待推送
     */
    WAIT_PUSH(1, "等待推送"),

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

    ;

    private Integer code;
    private String name;

    DistributionFlagEnum(Integer code, String name) {
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
            configs = new ArrayList<>(DistributionFlagEnum.values().length);
            for (DistributionFlagEnum typeEnum : DistributionFlagEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

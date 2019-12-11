package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单合并状态
 * @Author LGy
 * @Date 2019/12/11
 */
public enum OrderMergeEnum {

    /**
     * 等待合并状态
     */
    WAIT(0, "等待合并"),

    /**
     * 不合并
     */
    NOT_MERGE(1, "不合并"),

    /**
     * 已合并
     */
    MERGE(2, "已合并");

    private Integer code;
    private String name;

    OrderMergeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderMergeEnum.values().length);
            for (OrderMergeEnum typeEnum : OrderMergeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

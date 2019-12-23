package com.lgy.oms.enums.order;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 订单来源类型
 * @Author LGy
 * @Date 2019/12/10
 */
public enum OrderSourceTypeEnum {

    /**
     * 交易订单
     */
    TRADE(1, "交易订单"),
    /**
     * 奇门
     */
    QIMEN(2, "奇门 "),
    /**
     * 手动新增
     */
    HAND_ADD(3, "手动新增"),
    /**
     * 导入
     */
    IMPORT(4, "导入"),
    /**
     * 复制
     */
    COPY(5, "复制"),
    /**
     * 补发
     */
    REISSUE(6, "补发"),
    /**
     * 拆分
     */
    SPLIT(7, "拆分"),
    /**
     * 合并
     */
    COMBINE(8, "合并");

    private Integer code;
    private String name;

    OrderSourceTypeEnum(Integer code, String name) {
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
            configs = new ArrayList<>(OrderSourceTypeEnum.values().length);
            for (OrderSourceTypeEnum typeEnum : OrderSourceTypeEnum.values()) {
                configs.add(new Config(typeEnum.getCode() + "", typeEnum.getName()));
            }
        }
        return configs;
    }

}

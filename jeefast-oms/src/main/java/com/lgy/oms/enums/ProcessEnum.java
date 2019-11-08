package com.lgy.oms.enums;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程方式
 * @Author LGy
 * @Date 2019/11/1 17:01
 **/
public enum ProcessEnum {

    /**
     * 人工触发
     */
    HAND("0", "人工触发"),
    /**
     * 无逻辑转发
     */
    FORWARD("1", "无逻辑转发"),

    /**
     * 状态机触发
     */
    STATE_MACHINE("2", "状态机触发"),
    /**
     * 调度触发
     */
    JOBS("3", "调度触发");

    private final String key;
    private final String value;

    /**
     * 节点集合
     */
    private static List<Config> configs;

    /**
     * 获取节点集合
     */
    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>(ProcessEnum.values().length);
            for (ProcessEnum process : ProcessEnum.values()) {
                configs.add(new Config(process.getKey(), process.getValue()));
            }
        }
        return configs;
    }

    ProcessEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

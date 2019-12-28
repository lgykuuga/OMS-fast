package com.lgy.oms.constants;

/**
 * 日志级别类型
 *
 * @author LGy
 */
public enum TraceLevelType {


    /**
     * 记录订单轨迹
     */
    TRACE(0, "轨迹级别"),

    /**
     * 订单状态节点级别,以后状态节点可用于发送给别的系统
     */
    STATUS(1, "状态级别"),

    /**
     * 代码或设置异常级别,需要开发人员或运维人员引起关注
     */
    ABNORMAL(2, "异常级别");

    private Integer key;
    private String value;

    TraceLevelType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

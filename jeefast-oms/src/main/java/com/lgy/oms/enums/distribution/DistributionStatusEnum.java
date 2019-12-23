package com.lgy.oms.enums.distribution;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 配货单状态
 * @author Haru Skch
 * @since 2019-12-21
 */
public enum DistributionStatusEnum {
    /**
     * 等待配货
     */
    WAIT_DISTRIBUTION(0, "等待配货"),
    /**
     * 取消
     */
    CANCEL(1, "取消"),
    /**
     * 已配货
     */
    DISTRIBUTION(2, "已配货"),
    /**
     * 待发货
     *  delivery
     */
    WAIT_DELIVERY(3, "待发货"),
    /**
     * 已发货
     */
    DELIVERY(5, "已发货");

    private Integer value;
    private String name;

    private static List<Config> list = new ArrayList<>();

    DistributionStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Config> getList(){
        if(list.isEmpty()){
            for (DistributionStatusEnum temp : DistributionStatusEnum.values()) {
                list.add(new Config(temp.value.toString(), temp.getName()));
            }
        }
        return list;
    }
}

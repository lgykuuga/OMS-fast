package com.lgy.oms.enums.distibution;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 配货单状态
 * @author Haru Skch
 * @since 2019-12-21
 */
public enum DistributionLockWareHouseEnum {
    NONE(0, "未锁库"),

    LOCKED(2, "已锁库");

    private Integer value;
    private String name;

    private static List<Config> list = new ArrayList<>();

    DistributionLockWareHouseEnum(Integer value, String name) {
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
            for (DistributionLockWareHouseEnum temp : DistributionLockWareHouseEnum.values()) {
                list.add(new Config(temp.value.toString(), temp.getName()));
            }
        }
        return list;
    }
}

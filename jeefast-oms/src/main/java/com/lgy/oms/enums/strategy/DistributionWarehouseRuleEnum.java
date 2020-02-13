package com.lgy.oms.enums.strategy;

import com.lgy.system.domain.vo.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 匹配仓库规则
 * @Author LGy
 * @Date 2020/2/2
 */
public enum DistributionWarehouseRuleEnum {

    /**
     * 分仓规则
     */
    RULE_AVAILABLE(0, "配货策略可用仓库", "配货策略可用仓库"),
    RULE_SPECIAL(1, "特定信息分配仓库", "特定信息分配仓库"),
    RULE_LOGISTICS(2, "指定物流分配仓库", "指定物流分配仓库"),
    RULE_AREA(3, "覆盖区域到达仓库", "覆盖区域到达仓库"),
    RULE_WEIGHT(4,"根据重量分配仓库", "根据重量分配仓库"),
    RULE_DETAIL(5, "特定商品指定仓库", "特定商品指定仓库"),
    RULE_DEFAULT(6, "配货策略默认仓库", "配货策略默认仓库"),
    RULE_LOCK_STOCK(7, "可用库存分配仓库", "可用库存分配仓库");

    private Integer code;
    private String name;
    private String remark;

    DistributionWarehouseRuleEnum(Integer code, String name,  String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    /**
     * 策略集合
     */
    private static List<Config> configs;


    public final static List<Config> getList() {
        if (configs == null) {
            configs = new ArrayList<>(DistributionWarehouseRuleEnum.values().length);
            for (DistributionWarehouseRuleEnum strategyEnum : DistributionWarehouseRuleEnum.values()) {
                configs.add(new Config(strategyEnum.getCode().toString(), strategyEnum.getName()));
            }
        }
        return configs;
    }
}

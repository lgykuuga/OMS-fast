package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略分仓规则列表 oms_strategy_distribution_warehouse_list
 *
 * @author lgy
 * @date 2020-02-01
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_warehouse_rule")
public class StrategyDistributionWarehouseRule extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 策略编码 */
    private String gco;

    /** 规则编码 */
    private Integer ruleId;

    /** 优先级 */
    private Integer priority;

    /** 是否必须满足 */
    private Integer must;

    /** 状态（0启用 1停用） */
    private String status;

    /** 备注 */
    private String remark;

}

package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略分仓可用仓库表 oms_strategy_distribution_warehouse_available
 *
 * @author lgy
 * @date 2020-02-01
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_warehouse_available")
public class StrategyDistributionWarehouseAvailable extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 策略编码 */
    @Excel(name = "策略编码")
    private String gco;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouse;

    /** 优先级 */
    @Excel(name = "优先级")
    private Long priority;

    /** 自动推送 */
    @Excel(name = "自动推送")
    private Integer auto;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}

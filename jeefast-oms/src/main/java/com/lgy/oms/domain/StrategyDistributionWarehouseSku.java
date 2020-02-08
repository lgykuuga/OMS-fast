package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略仓库SKU信息表 oms_strategy_distribution_warehouse_sku
 *
 * @author lgy
 * @date 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_warehouse_sku")
public class StrategyDistributionWarehouseSku extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 策略编码
     */
    private String gco;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 仓库编码
     */
    private String warehouse;

    /**
     * sku编码
     */
    private String sku;

    /**
     * 是否拆分
     */
    private Integer split;

    /**
     * 状态（0启用 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}

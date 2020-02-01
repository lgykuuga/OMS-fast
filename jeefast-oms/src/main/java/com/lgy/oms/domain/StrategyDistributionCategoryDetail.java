package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略类别商品拆分明细表 oms_strategy_distribution_category_detail
 *
 * @author lgy
 * @date 2020-02-01
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_category_detail")
public class StrategyDistributionCategoryDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 对应category表自增id */
    @Excel(name = "对应category表自增id")
    private Long categoryId;

    /** 策略编码 */
    @Excel(name = "策略编码")
    private String gco;

    /** 类别编码 */
    @Excel(name = "类别编码")
    private String category;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

}

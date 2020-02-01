package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略特殊商品拆分明细表 oms_strategy_distribution_special_detail
 *
 * @author lgy
 * @date 2020-02-01
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_special_detail")
public class StrategyDistributionSpecialDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 对应special表自增id */
    @Excel(name = "对应special表自增id")
    private Long specialId;

    /** 策略编码 */
    @Excel(name = "策略编码")
    private String gco;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String commodity;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

}

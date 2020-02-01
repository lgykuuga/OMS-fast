package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略适用店铺表 oms_strategy_distribution_shop
 *
 * @author lgy
 * @date 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_shop")
public class StrategyDistributionShop extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 策略编码 */
    @Excel(name = "策略编码")
    private String gco;

    /** 适用店铺 */
    @Excel(name = "适用店铺")
    private String shop;

    /** 开启自动(0:开启,1:关闭) */
    @Excel(name = "开启自动(0:开启,1:关闭)")
    private String auto;

}

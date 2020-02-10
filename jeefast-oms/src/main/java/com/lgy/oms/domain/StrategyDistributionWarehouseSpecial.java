package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略特定信息分仓表 oms_strategy_distribution_warehouse_special
 *
 * @author lgy
 * @date 2020-02-01
 */
 @Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_warehouse_special")
public class StrategyDistributionWarehouseSpecial extends BaseEntity implements Serializable {
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
    private Integer priority;

    /** 属性类型(订单主信息/订单用户信息/订单支付信息...) */
    @Excel(name = "属性类型(订单主信息/订单用户信息/订单支付信息...)")
    private String type;

    /** 规则对应的字段 */
    @Excel(name = "规则对应的字段")
    private String field;

    /** 条件(大于/小于/等于/不等于/包含/正则) */
    @Excel(name = "条件(大于/小于/等于/不等于/包含/正则)")
    private String requirement;

    /** 匹配值(多条用英文逗号分隔) */
    @Excel(name = "匹配值(多条用英文逗号分隔)")
    private String valueCode;

    /** 匹配值(中文含义) */
    @Excel(name = "匹配值(中文含义)")
    private String valueName;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private Integer status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}

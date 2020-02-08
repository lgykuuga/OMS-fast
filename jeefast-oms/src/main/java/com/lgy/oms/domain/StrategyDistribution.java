package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 配货策略表 oms_strategy_distribution
 *
 * @author lgy
 * @date 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution")
public class StrategyDistribution extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 策略编码
     */
    @Excel(name = "策略编码")
    private String gco;

    /**
     * 策略名称
     */
    @Excel(name = "策略名称")
    private String gna;

    /**
     * 预分配开启
     */
    @Excel(name = "预分配开启")
    private Integer preExecute;

    /**
     * 仓库商品拆分开启
     */
    @Excel(name = "仓库商品拆分开启")
    private Integer warehouseSkuSplit;

    /**
     * 特殊商品拆分开启
     */
    @Excel(name = "特殊商品拆分开启")
    private Integer specialSkuSplit;

    /**
     * 类别商品拆分开启
     */
    @Excel(name = "类别商品拆分开启")
    private Integer categorySkuSplit;

    /**
     * 库存不足拆分开启
     */
    @Excel(name = "库存不足拆分开启")
    private Integer stockSplit;

    /**
     * 订单重量拆分开启
     */
    @Excel(name = "订单重量拆分开启")
    private Integer weightSplit;

    /**
     * 锁定库存模式
     */
    @Excel(name = "锁定库存模式")
    private Integer lockModel;

    /**
     * 重新分配仓库
     */
    @Excel(name = "重新分配仓库")
    private Integer reWarehouse;

    /**
     * 重新分配物流
     */
    @Excel(name = "重新分配物流")
    private Integer reLogistics;

    /**
     * 配货模式
     */
    @Excel(name = "配货模式")
    private Integer model;

    /**
     * 自动配货等待时间(分钟)
     */
    @Excel(name = "自动配货等待时间(分钟)")
    private Long waitTime;

    /**
     * 货主
     */
    @Excel(name = "货主")
    private String owner;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 策略对应预分配信息配置
     */
    @TableField(exist = false)
    private List<StrategyDistributionPre> distributionPreList;
    /**
     * 策略对应特殊商品拆分信息配置
     */
    @TableField(exist = false)
    private List<StrategyDistributionSpecial> distributionSpecialList;
    /**
     * 策略对应类别商品拆分信息配置
     */
    @TableField(exist = false)
    private List<StrategyDistributionCategory> distributionCategoryList;
    /**
     * 策略对应仓库SKU信息配置
     */
    @TableField(exist = false)
    private List<StrategyDistributionWarehouseSku> distributionWarehouseSkuList;
    /**
     * 策略对应分仓规则信息配置
     */
    @TableField(exist = false)
    private List<StrategyDistributionWarehouseRule> distributionWarehouseRules;

}

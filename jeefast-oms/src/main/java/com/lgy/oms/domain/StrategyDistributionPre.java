package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 配货策略预分配信息表 oms_strategy_distribution_pre
 *
 * @author lgy
 * @date 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_distribution_pre")
public class StrategyDistributionPre extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 策略编码 */
    @Excel(name = "策略编码")
    private String gco;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String gna;

    /** 优先级 */
    @Excel(name = "优先级")
    private Long priority;

    /** 满足条件个数 */
    @Excel(name = "满足条件个数")
    private Long number;

    /** 参与仓库商品拆分 */
    @Excel(name = "参与仓库商品拆分")
    private Integer warehouseSkuSplit;

    /** 参与特殊商品拆分 */
    @Excel(name = "参与特殊商品拆分")
    private Integer specialSkuSplit;

    /** 参与类别商品拆分 */
    @Excel(name = "参与类别商品拆分")
    private Integer categorySkuSplit;

    /** 参与库存不足拆分 */
    @Excel(name = "参与库存不足拆分")
    private Integer stockSplit;

    /** 参与订单重量拆分 */
    @Excel(name = "参与订单重量拆分")
    private Integer weightSplit;

    /** 锁定库存模式 */
    @Excel(name = "锁定库存模式")
    private Integer lockModel;

    /** 发货仓库编码 */
    @Excel(name = "发货仓库编码")
    private String warehouse;

    /** 物流商编码 */
    @Excel(name = "物流商编码")
    private String logistics;

    /** 状态（0启用 1停用） */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}

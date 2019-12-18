package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 审单策略表 oms_strategy_audit
 *
 * @author lgy
 * @date 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_audit")
public class StrategyAudit extends BaseEntity implements Serializable {
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
     * 来源单号相同拦截开启
     */
    @Excel(name = "来源单号相同拦截开启")
    private Integer source;

    /**
     * 地址有效性校验开启
     */
    @Excel(name = "地址有效性校验开启")
    private Integer addressValid;

    /**
     * 买家留言拦截开启
     */
    @Excel(name = "买家留言拦截开启")
    private Integer buyMessage;

    /**
     * 卖家留言拦截开启
     */
    @Excel(name = "卖家留言拦截开启")
    private Integer sellerMessage;

    /**
     * 卖家留言匹配开启
     */
    @Excel(name = "卖家留言匹配开启")
    private Integer sellerMessageMatch;

    /**
     * 货到付款拦截开启
     */
    @Excel(name = "货到付款拦截开启")
    private Integer cod;

    /**
     * 卖家备注旗帜拦截开启
     */
    @Excel(name = "卖家备注旗帜拦截开启")
    private Integer sellerFlag;

    /**
     * 退款拦截开启
     */
    @Excel(name = "退款拦截开启")
    private Integer refund;

    /**
     * 标记拦截开启(多选)
     */
    @Excel(name = "标记拦截开启(多选)")
    private String mark;

    /**
     * 金额拦截类型
     */
    @Excel(name = "金额拦截类型")
    private Integer amount;

    /**
     * 金额拦截最小值
     */
    @Excel(name = "金额拦截最小值")
    private BigDecimal amountMin;

    /**
     * 金额拦截最小值
     */
    @Excel(name = "金额拦截最小值")
    private BigDecimal amountMax;

    /**
     * 时间范围拦截类型拦截
     */
    @Excel(name = "时间范围拦截类型拦截")
    private Integer timeRange;

    /**
     * 时间拦截起
     */
    @Excel(name = "时间拦截起", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timeStart;

    /**
     * 时间拦截止
     */
    @Excel(name = "时间拦截止", width = 30, dateFormat = "yyyy-MM-dd")
    private Date timeEnd;

    /**
     * 数字拦截类型
     */
    @Excel(name = "数字拦截类型")
    private Integer number;

    /**
     * 数字拦截最小值
     */
    @Excel(name = "数字拦截最小值")
    private Integer numberMin;

    /**
     * 数字拦截最小值
     */
    @Excel(name = "数字拦截最小值")
    private Integer numberMax;

    /**
     * 有效期类型拦截
     */
    @Excel(name = "有效期类型拦截")
    private Integer validDateType;

    /**
     * 有效期(天)
     */
    @Excel(name = "有效期(天)")
    private Integer validDate;

    /**
     * 自动审核等待时间类型
     */
    @Excel(name = "自动审核等待时间类型")
    private Integer autoAuditType;

    /**
     * 等待时间(分钟)
     */
    @Excel(name = "等待时间(分钟)")
    private Integer waitMinute;

    /**
     * 毛利率拦截值
     */
    @Excel(name = "毛利率拦截值")
    private Integer profitValue;

    /**
     * 特定信息拦截是否开启
     */
    @Excel(name = "特定信息拦截是否开启")
    private Integer orderIntercept;

    /**
     * 指定商品拦截是否开启
     */
    @Excel(name = "指定商品拦截是否开启")
    private Integer commodityIntercept;

    /**
     * 特殊地址拦截是否开启
     */
    @Excel(name = "特殊地址拦截是否开启")
    private Integer addressIntercept;

    /**
     * 组合信息拦截是否开启
     */
    @Excel(name = "组合信息是否开启")
    private Integer comboIntercept;

    /** 货主 */
    @Excel(name = "货主")
    private String owner;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

}

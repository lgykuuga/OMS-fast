package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 审单策略组合信息拦截表 oms_strategy_audit_combo
 *
 * @author lgy
 * @date 2020-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_audit_combo")
public class StrategyAuditCombo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 规则编码
     */
    private String gco;

    /**
     * 规则名称
     */
    private String gna;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 满足条件个数
     */
    private Integer number;

    /**
     * 状态（0启用 1停用）
     */
    private String status;

    /**
     * 说明
     */
    private String remark;

}

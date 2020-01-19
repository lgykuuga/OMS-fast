package com.lgy.oms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgy.common.annotation.Excel;
import com.lgy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 转单策略表 oms_strategy_convert
 *
 * @author lgy
 * @date 2019-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_strategy_convert")
public class StrategyConvert extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** null */
    private Long id;

    /** 策略编码 */
    private String gco;

    /** 策略名称 */
    private String gna;

    /** 触发节点 */
    private Integer triggerNode;

    /** 匹配商品方式(0:外部编码,1:铺货关系) */
    private String matchCommodity;

    /** 事件驱动(0:手动触发,1:无逻辑转发,2:状态机触发,3:调度触发)
     * @see com.lgy.oms.enums.strategy.ProcessEnum
     * */
    private String process;

    /** 货主 */
    private String owner;

    /** 备注 */
    private String remark;

}

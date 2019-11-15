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
    @Excel(name = "策略编码")
    private String gco;

    /** 策略名称 */
    @Excel(name = "策略名称")
    private String gna;

    /** 触发节点 */
    @Excel(name = "触发节点")
    private Integer triggerNode;

    /** 匹配商品方式(0:外部编码,1:铺货关系) */
    @Excel(name = "匹配商品方式(0:外部编码,1:铺货关系)")
    private String matchCommodity;

    /** 流程方式(0:无逻辑转发,1:状态机触发,2:调度触发) */
    @Excel(name = "流程方式(0:无逻辑转发,1:状态机触发,2:调度触发)")
    private String process;

    /** 货主 */
    @Excel(name = "货主")
    private String owner;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGco() {
        return gco;
    }

    public void setGco(String gco) {
        this.gco = gco;
    }

    public String getGna() {
        return gna;
    }

    public void setGna(String gna) {
        this.gna = gna;
    }

    public Integer getTriggerNode() {
        return triggerNode;
    }

    public void setTriggerNode(Integer triggerNode) {
        this.triggerNode = triggerNode;
    }

    public String getMatchCommodity() {
        return matchCommodity;
    }

    public void setMatchCommodity(String matchCommodity) {
        this.matchCommodity = matchCommodity;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

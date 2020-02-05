package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;

import java.util.List;

/**
 * 配货策略分仓规则列 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionWarehouseRuleService extends IService<StrategyDistributionWarehouseRule> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionWarehouseRule> getStrategyByGco(String gco);

    /**
     * 初始化分仓规则列表
     *
     * @param gco 策略编码
     * @return
     */
    CommonResponse<String> initRule(String gco);

    /**
     * 调整分仓规则顺序优先级顺序
     *
     * @param id 组合信息id
     * @param i  序号
     * @return
     */
    boolean updatePrePriority(Long id, int i);

    /**
     * 更改字段值
     *
     * @param id
     * @param field 修改字段
     * @param value 更新值
     * @return
     */
    boolean changeField(Long id, String field, Integer value) throws Exception;
}
package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
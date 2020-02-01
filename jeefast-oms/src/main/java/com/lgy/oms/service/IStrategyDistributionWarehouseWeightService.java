package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionWarehouseWeight;

import java.util.List;

/**
 * 配货策略分仓重量规则 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionWarehouseWeightService extends IService<StrategyDistributionWarehouseWeight> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionWarehouseWeight> getStrategyByGco(String gco);
}
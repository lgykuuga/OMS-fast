package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionWarehouseSpecial;

import java.util.List;

/**
 * 配货策略特定信息分仓 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionWarehouseSpecialService extends IService<StrategyDistributionWarehouseSpecial> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionWarehouseSpecial> getStrategyByGco(String gco);
}
package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionCategory;

import java.util.List;

/**
 * 配货策略类别商品拆分 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionCategoryService extends IService<StrategyDistributionCategory> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionCategory> getStrategyByGco(String gco);

}
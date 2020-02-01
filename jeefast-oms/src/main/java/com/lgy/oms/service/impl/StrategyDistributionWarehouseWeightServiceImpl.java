package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionWarehouseSpecial;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyDistributionWarehouseWeightMapper;
import com.lgy.oms.domain.StrategyDistributionWarehouseWeight;
import com.lgy.oms.service.IStrategyDistributionWarehouseWeightService;

import java.util.List;

/**
 * 配货策略分仓重量规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseWeightServiceImpl extends ServiceImpl<StrategyDistributionWarehouseWeightMapper, StrategyDistributionWarehouseWeight> implements IStrategyDistributionWarehouseWeightService {

    @Override
    public List<StrategyDistributionWarehouseWeight> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseWeight> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
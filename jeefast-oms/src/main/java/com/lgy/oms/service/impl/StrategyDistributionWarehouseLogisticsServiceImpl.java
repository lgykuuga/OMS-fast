package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionWarehouseLogistics;
import com.lgy.oms.mapper.StrategyDistributionWarehouseLogisticsMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseLogisticsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略分仓物流规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseLogisticsServiceImpl extends ServiceImpl<StrategyDistributionWarehouseLogisticsMapper, StrategyDistributionWarehouseLogistics> implements IStrategyDistributionWarehouseLogisticsService {

    @Override
    public List<StrategyDistributionWarehouseLogistics> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseLogistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
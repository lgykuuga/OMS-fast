package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.mapper.StrategyDistributionWarehouseAvailableMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略分仓可用仓库 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseAvailableServiceImpl extends ServiceImpl<StrategyDistributionWarehouseAvailableMapper, StrategyDistributionWarehouseAvailable> implements IStrategyDistributionWarehouseAvailableService {

    @Override
    public List<StrategyDistributionWarehouseAvailable> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseAvailable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
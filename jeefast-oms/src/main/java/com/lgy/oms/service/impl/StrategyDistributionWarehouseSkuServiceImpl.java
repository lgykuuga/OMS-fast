package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionWarehouseSku;
import com.lgy.oms.mapper.StrategyDistributionWarehouseSkuMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseSkuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略仓库SKU信息 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseSkuServiceImpl extends ServiceImpl<StrategyDistributionWarehouseSkuMapper, StrategyDistributionWarehouseSku> implements IStrategyDistributionWarehouseSkuService {

    @Override
    public List<StrategyDistributionWarehouseSku> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
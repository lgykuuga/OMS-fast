package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionWarehouseSpecial;
import com.lgy.oms.mapper.StrategyDistributionWarehouseSpecialMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseSpecialService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略特定信息分仓 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseSpecialServiceImpl extends ServiceImpl<StrategyDistributionWarehouseSpecialMapper, StrategyDistributionWarehouseSpecial> implements IStrategyDistributionWarehouseSpecialService {

    @Override
    public List<StrategyDistributionWarehouseSpecial> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseSpecial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseWeight;
import com.lgy.oms.mapper.StrategyDistributionWarehouseWeightMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseWeightService;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseWeight entity = new StrategyDistributionWarehouseWeight();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseWeight rule = new StrategyDistributionWarehouseWeight();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

}
package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.reflect.ReflectUtils;
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

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseLogistics entity = new StrategyDistributionWarehouseLogistics();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseLogistics rule = new StrategyDistributionWarehouseLogistics();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }
}
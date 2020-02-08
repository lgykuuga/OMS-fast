package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.reflect.ReflectUtils;
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

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseSku entity = new StrategyDistributionWarehouseSku();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseSku rule = new StrategyDistributionWarehouseSku();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

}
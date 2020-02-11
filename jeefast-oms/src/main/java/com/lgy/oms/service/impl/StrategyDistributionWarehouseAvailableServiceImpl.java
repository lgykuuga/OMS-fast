package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.mapper.StrategyDistributionWarehouseAvailableMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseAvailable entity = new StrategyDistributionWarehouseAvailable();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseAvailable rule = new StrategyDistributionWarehouseAvailable();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

    @Override
    public List<String> getAvailableWarehouse(String gco) {
        QueryWrapper<StrategyDistributionWarehouseAvailable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        //开启的仓库
        queryWrapper.eq("status", Constants.ON);
        //优先级排序
        queryWrapper.orderByAsc("priority");
        queryWrapper.select("warehouse");
        List<StrategyDistributionWarehouseAvailable> list = this.list(queryWrapper);

        if (list == null || list.isEmpty()) {
            return null;
        }

        List<String> warehouseList = new ArrayList<>(list.size());

        for (StrategyDistributionWarehouseAvailable strategyDistributionWarehouseAvailable : list) {
            warehouseList.add(strategyDistributionWarehouseAvailable.getWarehouse());
        }

        return warehouseList;
    }

}
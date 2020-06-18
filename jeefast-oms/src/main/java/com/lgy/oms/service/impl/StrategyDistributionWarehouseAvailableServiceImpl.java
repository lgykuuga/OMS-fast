package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.mapper.StrategyDistributionWarehouseAvailableMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseAvailableService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 配货策略分仓可用仓库 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseAvailableServiceImpl extends ServiceImpl<StrategyDistributionWarehouseAvailableMapper,
        StrategyDistributionWarehouseAvailable> implements IStrategyDistributionWarehouseAvailableService {

    @Override
    public List<StrategyDistributionWarehouseAvailable> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseAvailable> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StrategyDistributionWarehouseAvailable::getGco, gco);
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
        queryWrapper.lambda()
                .eq(StrategyDistributionWarehouseAvailable::getGco, gco)
                //开启的仓库
                .eq(StrategyDistributionWarehouseAvailable::getStatus, Constants.ON)
                //优先级排序
                .orderByAsc(StrategyDistributionWarehouseAvailable::getPriority)
                .select(StrategyDistributionWarehouseAvailable::getWarehouse);

        List<StrategyDistributionWarehouseAvailable> list = this.list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        return list.stream().map(StrategyDistributionWarehouseAvailable::getWarehouse).collect(Collectors.toList());
    }

}
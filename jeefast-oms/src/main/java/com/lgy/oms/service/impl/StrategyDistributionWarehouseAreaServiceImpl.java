package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseArea;
import com.lgy.oms.domain.StrategyDistributionWarehouseAvailable;
import com.lgy.oms.mapper.StrategyDistributionWarehouseAreaMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略分仓覆盖区域规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseAreaServiceImpl extends ServiceImpl<StrategyDistributionWarehouseAreaMapper, StrategyDistributionWarehouseArea> implements IStrategyDistributionWarehouseAreaService {

    @Override
    public List<StrategyDistributionWarehouseArea> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseArea entity = new StrategyDistributionWarehouseArea();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseArea rule = new StrategyDistributionWarehouseArea();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }
}
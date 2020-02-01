package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyAuditSpecial;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyDistributionCategoryMapper;
import com.lgy.oms.domain.StrategyDistributionCategory;
import com.lgy.oms.service.IStrategyDistributionCategoryService;

import java.util.List;

/**
 * 配货策略类别商品拆分 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionCategoryServiceImpl extends ServiceImpl<StrategyDistributionCategoryMapper, StrategyDistributionCategory> implements IStrategyDistributionCategoryService {

    @Override
    public List<StrategyDistributionCategory> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }


}
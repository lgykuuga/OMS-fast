package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionCategoryDetail;
import com.lgy.oms.mapper.StrategyDistributionCategoryDetailMapper;
import com.lgy.oms.service.IStrategyDistributionCategoryDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略类别商品拆分明细 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionCategoryDetailServiceImpl extends ServiceImpl<StrategyDistributionCategoryDetailMapper, StrategyDistributionCategoryDetail> implements IStrategyDistributionCategoryDetailService {

    @Override
    public List<StrategyDistributionCategoryDetail> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionCategoryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionSpecialDetail;
import com.lgy.oms.mapper.StrategyDistributionSpecialDetailMapper;
import com.lgy.oms.service.IStrategyDistributionSpecialDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略特殊商品拆分明细 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionSpecialDetailServiceImpl extends ServiceImpl<StrategyDistributionSpecialDetailMapper, StrategyDistributionSpecialDetail> implements IStrategyDistributionSpecialDetailService {

    @Override
    public List<StrategyDistributionSpecialDetail> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionSpecialDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
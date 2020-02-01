package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionPreDetail;
import com.lgy.oms.mapper.StrategyDistributionPreDetailMapper;
import com.lgy.oms.service.IStrategyDistributionPreDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略预分配信息明细 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionPreDetailServiceImpl extends ServiceImpl<StrategyDistributionPreDetailMapper, StrategyDistributionPreDetail> implements IStrategyDistributionPreDetailService {

    @Override
    public List<StrategyDistributionPreDetail> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionPreDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
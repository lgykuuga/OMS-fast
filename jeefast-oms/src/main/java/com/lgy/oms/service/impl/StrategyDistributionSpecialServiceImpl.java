package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionSpecial;
import com.lgy.oms.mapper.StrategyDistributionSpecialMapper;
import com.lgy.oms.service.IStrategyDistributionSpecialService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略特殊商品拆分 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionSpecialServiceImpl extends ServiceImpl<StrategyDistributionSpecialMapper, StrategyDistributionSpecial> implements IStrategyDistributionSpecialService {

    @Override
    public List<StrategyDistributionSpecial> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionSpecial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }
}
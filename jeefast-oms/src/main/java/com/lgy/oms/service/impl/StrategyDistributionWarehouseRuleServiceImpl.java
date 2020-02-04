package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.enums.strategy.DistributionWarehouseRuleEnum;
import com.lgy.oms.mapper.StrategyDistributionWarehouseRuleMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略分仓规则列 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseRuleServiceImpl extends ServiceImpl<StrategyDistributionWarehouseRuleMapper, StrategyDistributionWarehouseRule> implements IStrategyDistributionWarehouseRuleService {

    @Override
    public List<StrategyDistributionWarehouseRule> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public CommonResponse<String> initRule(String gco) {

        //删除原有规则
        QueryWrapper<StrategyDistributionWarehouseRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        remove(queryWrapper);
        //重新生成规则
        for (int i = 0; i < DistributionWarehouseRuleEnum.values().length; i++) {
            
        }




        return null;
    }
}
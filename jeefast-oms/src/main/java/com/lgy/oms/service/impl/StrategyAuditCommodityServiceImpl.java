package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyAuditSpecial;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyAuditCommodityMapper;
import com.lgy.oms.domain.StrategyAuditCommodity;
import com.lgy.oms.service.IStrategyAuditCommodityService;

import java.util.List;

/**
 * 审单策略指定商品拦截 服务层实现
 *
 * @author lgy
 * @date 2019-12-31
 */
@Service
public class StrategyAuditCommodityServiceImpl extends
        ServiceImpl<StrategyAuditCommodityMapper, StrategyAuditCommodity> implements IStrategyAuditCommodityService {

    @Override
    public List<StrategyAuditCommodity> getStrategyByGco(String gco) {
        QueryWrapper<StrategyAuditCommodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        //TODO 可以根据类型先分类合并,用于审单
        return this.list(queryWrapper);
    }
}
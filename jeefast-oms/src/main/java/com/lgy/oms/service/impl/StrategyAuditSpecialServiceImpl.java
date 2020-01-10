package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyAuditSpecialMapper;
import com.lgy.oms.domain.StrategyAuditSpecial;
import com.lgy.oms.service.IStrategyAuditSpecialService;

import java.util.List;

/**
 * 审单策略特定信息拦截 服务层实现
 *
 * @author lgy
 * @date 2019-12-31
 */
@Service
public class StrategyAuditSpecialServiceImpl extends ServiceImpl<StrategyAuditSpecialMapper, StrategyAuditSpecial> implements IStrategyAuditSpecialService {

    @Override
    public List<StrategyAuditSpecial> getStrategyByGco(String gco) {
        QueryWrapper<StrategyAuditSpecial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        //TODO 可以根据类型先分类合并,用于审单
        return this.list(queryWrapper);
    }
}
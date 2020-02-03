package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.StrategyDistributionPre;
import com.lgy.oms.mapper.StrategyDistributionPreMapper;
import com.lgy.oms.service.IStrategyDistributionPreService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配货策略预分配信息 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionPreServiceImpl extends ServiceImpl<StrategyDistributionPreMapper, StrategyDistributionPre> implements IStrategyDistributionPreService {

    @Override
    public List<StrategyDistributionPre> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionPre> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionPre entity = new StrategyDistributionPre();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

}
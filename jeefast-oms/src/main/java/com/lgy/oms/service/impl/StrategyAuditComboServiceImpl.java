package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyAuditComboMapper;
import com.lgy.oms.domain.StrategyAuditCombo;
import com.lgy.oms.service.IStrategyAuditComboService;

/**
 * 审单策略组合信息拦截 服务层实现
 *
 * @author lgy
 * @date 2020-01-20
 */
@Service
public class StrategyAuditComboServiceImpl extends ServiceImpl<StrategyAuditComboMapper, StrategyAuditCombo> implements IStrategyAuditComboService {

    @Override
    public boolean updateComboPriority(Long id, int i) {
        StrategyAuditCombo entity = new StrategyAuditCombo();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }
}
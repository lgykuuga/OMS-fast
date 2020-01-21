package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StrategyAuditComboDetailMapper;
import com.lgy.oms.domain.StrategyAuditComboDetail;
import com.lgy.oms.service.IStrategyAuditComboDetailService;

import java.util.List;

/**
 * 审单策略组合信息明细 服务层实现
 *
 * @author lgy
 * @date 2020-01-21
 */
@Service
public class StrategyAuditComboDetailServiceImpl extends ServiceImpl<StrategyAuditComboDetailMapper, StrategyAuditComboDetail> implements IStrategyAuditComboDetailService {

    @Override
    public List<StrategyAuditComboDetail> getDetailByComboId(Long comboId) {
        QueryWrapper<StrategyAuditComboDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("combo_id", comboId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean deleteByComboId(String comboId) {
        QueryWrapper<StrategyAuditComboDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("combo_id", comboId);
        return this.remove(queryWrapper);
    }
}
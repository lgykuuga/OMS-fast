package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyAuditCombo;

/**
 * 审单策略组合信息拦截 服务层
 *
 * @author lgy
 * @date 2020-01-20
 */
public interface IStrategyAuditComboService extends IService<StrategyAuditCombo> {

    /**
     * 调整组合信息拦截优先级顺序
     *
     * @param id 组合信息id
     * @param i  序号
     * @return
     */
    boolean updateComboPriority(Long id, int i);
}
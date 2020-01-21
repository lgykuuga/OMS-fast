package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyAuditComboDetail;

import java.util.List;

/**
 * 审单策略组合信息明细 服务层
 *
 * @author lgy
 * @date 2020-01-21
 */
public interface IStrategyAuditComboDetailService extends IService<StrategyAuditComboDetail> {

    /**
     * 根据组合信息ID获取审单策略组合信息明细
     *
     * @param comboId 组合信息ID
     * @return
     */
    List<StrategyAuditComboDetail> getDetailByComboId(Long comboId);

    /**
     * 根据组合信息ID删除审单策略组合信息明细
     *
     * @param comboId
     * @return
     */
    boolean deleteByComboId(String comboId);
}
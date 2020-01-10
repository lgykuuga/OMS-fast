package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyAuditCommodity;

import java.util.List;

/**
 * 审单策略指定商品拦截 服务层
 *
 * @author lgy
 * @date 2019-12-31
 */
public interface IStrategyAuditCommodityService extends IService<StrategyAuditCommodity> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyAuditCommodity> getStrategyByGco(String gco);
}
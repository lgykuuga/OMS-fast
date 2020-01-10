package com.lgy.oms.service;

import com.lgy.oms.domain.StrategyAuditSpecial;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 审单策略特定信息拦截 服务层
 *
 * @author lgy
 * @date 2019-12-31
 */
public interface IStrategyAuditSpecialService extends IService<StrategyAuditSpecial> {

    /**
     * 根据策略编码获取策略
     * @param gco 策略编码
     * @return
     */
    List<StrategyAuditSpecial> getStrategyByGco(String gco);

}
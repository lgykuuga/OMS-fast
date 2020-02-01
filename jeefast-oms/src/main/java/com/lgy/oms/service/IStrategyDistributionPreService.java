package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionPre;

import java.util.List;

/**
 * 配货策略预分配信息 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionPreService extends IService<StrategyDistributionPre> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionPre> getStrategyByGco(String gco);
}
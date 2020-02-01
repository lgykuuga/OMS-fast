package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionPreDetail;

import java.util.List;

/**
 * 配货策略预分配信息明细 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionPreDetailService extends IService<StrategyDistributionPreDetail> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionPreDetail> getStrategyByGco(String gco);
}
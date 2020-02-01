package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionSpecialDetail;

import java.util.List;

/**
 * 配货策略特殊商品拆分明细 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionSpecialDetailService extends IService<StrategyDistributionSpecialDetail> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionSpecialDetail> getStrategyByGco(String gco);
}
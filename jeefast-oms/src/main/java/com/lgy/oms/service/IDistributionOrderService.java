package com.lgy.oms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.distribution.DistributionOrder;

/**
 * 配货单信息 服务层
 *
 * @author lgy
 * @date 2020-01-30
 */
public interface IDistributionOrderService extends IService<DistributionOrder> {

    /**
     * 保存配货单信息
     *
     * @param distributionOrder 配货单
     * @return 成功标识
     */
    boolean saveOrder(DistributionOrder distributionOrder);
}
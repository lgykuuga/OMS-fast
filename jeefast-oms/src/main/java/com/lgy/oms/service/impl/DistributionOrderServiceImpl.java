package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.mapper.DistributionOrderMapper;
import com.lgy.oms.service.IDistributionDetailService;
import com.lgy.oms.service.IDistributionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配货单信息 服务层实现
 *
 * @author lgy
 * @date 2020-01-30
 */
@Service
public class DistributionOrderServiceImpl extends ServiceImpl<DistributionOrderMapper, DistributionOrder> implements IDistributionOrderService {

    @Autowired
    IDistributionDetailService detailService;

    @Override
    public boolean saveOrder(DistributionOrder distributionOrder) {

        boolean b = this.save(distributionOrder);
        if (b) {
            detailService.saveBatch(distributionOrder.getDistributionDetail());
        }

        return b;
    }
}
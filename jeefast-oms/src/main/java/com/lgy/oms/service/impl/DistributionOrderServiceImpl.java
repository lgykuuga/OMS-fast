package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.distribution.DistributionOrder;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.DistributionOrderMapper;
import com.lgy.oms.service.IDistributionOrderService;

/**
 * 配货单信息 服务层实现
 *
 * @author lgy
 * @date 2020-01-30
 */
@Service
public class DistributionOrderServiceImpl extends ServiceImpl<DistributionOrderMapper, DistributionOrder> implements IDistributionOrderService {

}
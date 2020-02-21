package com.lgy.oms.biz.impl.distribution;


import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.IOrderLockStockService;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.service.IDistributionOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 订单锁定(占用)库存实现
 * @Author LGy
 * @Date 2020/2/21
 */
@Service
public class OrderLockStockServiceImpl implements IOrderLockStockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDistributionOrderService distributionOrderService;

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;


    @Override
    public CommonResponse<DistributionOrder> execute(OrderMain orderMain, StrategyDistribution strategyDistribution,
                                                     DistributionParamDTO param, List<String> warehouseList) {



        return null;
    }
}

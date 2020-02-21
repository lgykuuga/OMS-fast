package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;

import java.util.List;

/**
 * @Description 订单锁定(占用)库存
 * @Author LGy
 * @Date 2020/1/31
 */
public interface IOrderLockStockService {

    /**
     * 订单锁定(占用)库存
     *
     * @param orderMain            订单主信息
     * @param strategyDistribution 配货策略
     * @param param                请求配货参数
     * @param warehouseList        可用仓库列表
     * @return 锁库结果信息
     */
    CommonResponse<DistributionOrder> execute(OrderMain orderMain, StrategyDistribution strategyDistribution,
                                              DistributionParamDTO param, List<String> warehouseList);


}

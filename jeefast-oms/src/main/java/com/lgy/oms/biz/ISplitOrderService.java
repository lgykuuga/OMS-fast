package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;


/**
 * @Description 拆分订单服务接口
 * @Author LGy
 * @Date 2020/1/30
 */
public interface ISplitOrderService {


    /**
     * 开始标准拆分订单(第一次拆分)
     *
     * @param orderMain            订单主信息
     * @param strategyDistribution 配货策略
     * @param param                配货参数
     * @return
     */
    CommonResponse<String> standardSplit(OrderMain orderMain, StrategyDistribution strategyDistribution, DistributionParamDTO param);
}

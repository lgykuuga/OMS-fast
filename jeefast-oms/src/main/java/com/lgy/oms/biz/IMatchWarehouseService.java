package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;

import java.util.List;


/**
 * @Description 匹配仓库逻辑
 * @Author LGy
 * @Date 2020/2/11
 */
public interface IMatchWarehouseService {

    /**
     * 开始匹配仓库
     *
     * @param orderMain            订单信息
     * @param strategyDistribution 配货策略
     * @param param                请求配货参数
     * @return 配货可用仓库列表
     */
    CommonResponse<List<String>> start(OrderMain orderMain, StrategyDistribution strategyDistribution, DistributionParamDTO param);
}

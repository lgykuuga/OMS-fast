package com.lgy.oms.service.business;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;

import java.util.List;

/**
 * @Description 订单明细处理Service
 * @Author LGy
 * @Date 2019/12/2
 */
public interface IOrderDetailProcessingService {

    /**
     * 订单明细解析组合商品
     *
     * @param orderDetail
     * @return
     */
    CommonResponse<List<OrderDetail>> analysisCombCommodity(OrderDetail orderDetail);

    /**
     * 匹配商品编码
     *
     * @param orderMain 主订单信息
     * @param strategy  转单策略
     * @return
     */
    OrderMain matchCommodity(OrderMain orderMain, StrategyConvert strategy);

    /**
     * 订单主信息解析组合商品
     * @param orderMain
     * @return
     */
    OrderMain analysisCombCommodity(OrderMain orderMain);
}

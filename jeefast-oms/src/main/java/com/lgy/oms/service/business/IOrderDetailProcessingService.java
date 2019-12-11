package com.lgy.oms.service.business;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.order.OrderDetail;

import java.util.List;

/**
 * @Description 订单明细处理Service
 * @Author LGy
 * @Date 2019/12/2
 */
public interface IOrderDetailProcessingService {

    /**
     * 订单明细解析组合商品
     * @param orderDetail
     * @return
     */
    CommonResponse<List<OrderDetail>> analysisCombCommodity(OrderDetail orderDetail);

}

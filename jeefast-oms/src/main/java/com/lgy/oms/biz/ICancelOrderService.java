package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.common.dto.OrderDTO;

/**
 * @Description 取消订单服务接口
 * @Author LGy
 * @Date 2019/10/15
 */
public interface ICancelOrderService {

    /**
     * 取消订单
     *
     * @param orderDTO 订单传输对象
     * @return
     */
    CommonResponse<String> cancelOrder(OrderDTO orderDTO);
}

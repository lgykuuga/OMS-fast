package com.lgy.oms.biz.impl;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.interfaces.common.dto.OrderDTO;
import com.lgy.oms.biz.ICancelOrderService;
import org.springframework.stereotype.Service;

/**
 * @Description 取消订单服务实现
 * @Author LGy
 * @Date 2019/10/15
 */
@Service
public class CancelOrderServiceImpl implements ICancelOrderService {

    @Override
    public CommonResponse<String> cancelOrder(OrderDTO orderDTO) {
        return null;
    }
}

package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;


/**
 * @Description 订单履约服务接口
 * @Author LGy
 * @Date 2020/1/30
 */
public interface IFulfillService {

    /**
     * 订单配货动作
     *
     * @param orderId 订单号
     * @param param   其它参数
     * @return
     */
    CommonResponse<String> fulfillOrder(String orderId, DistributionParamDTO param);

    /**
     * 开始配货订单
     *
     * @param orderMain 订单主信息
     * @param param     配货参数
     * @return
     */
    CommonResponse<String> start(OrderMain orderMain, DistributionParamDTO param);
}

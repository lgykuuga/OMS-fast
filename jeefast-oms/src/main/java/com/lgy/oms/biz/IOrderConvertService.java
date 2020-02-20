package com.lgy.oms.biz;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;

/**
 * @Description 审核订单转换成配货订单
 * @Author LGy
 * @Date 2020/1/31
 */
public interface IOrderConvertService {

    /**
     * 交审核订单转换成配货订单
     *
     * @param orderMain 订单主信息
     * @param param     其它参数
     * @return
     */
    CommonResponse<DistributionOrder> execute(OrderMain orderMain, DistributionParamDTO param);


}

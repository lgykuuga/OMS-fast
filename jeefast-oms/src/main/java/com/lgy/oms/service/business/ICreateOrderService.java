package com.lgy.oms.service.business;

import java.util.List;
import java.util.Map;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.OrderDTO;


/**
 * @Description 创建订单Service
 * @Author LGy
 * @Date 2019/10/14
 */
public interface ICreateOrderService {

	/**
	 * 分类订单
	 * @param orderList
	 * @return
	 */
	Map<String, List<OrderDTO>> classifiedOrders(List<OrderDTO> orderList);

	/**
	 * 更新订单信息
	 * @param updateTradeList
	 * @return 更新数量
	 */
	CommonResponse<Integer> updateTradeInfo(List<Trade> updateTradeList);
}

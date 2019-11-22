package com.lgy.oms.service.business;



import com.lgy.oms.interfaces.common.dto.OrderDTO;

import java.util.List;

/**
 * @Description 异步处理订单Service
 * @Author LGy
 * @Date 2019/10/15
 */
public interface IAsyncExecuteOrderService {

	/**
	 * 异步更新订单状态
	 * @param updateList 待更新订单列表
	 */
	void updateOrderStatus(List<OrderDTO> updateList);

	/**
	 * 异步取消订单
	 * @param cancelList 待取消订单列表
	 */
	void cancelOrderStatus(List<OrderDTO> cancelList);
}

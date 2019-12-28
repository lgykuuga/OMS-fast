package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderStatusInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单状态 数据层
 *
 * @author lgy
 * @date 2019-12-13
 */
@Mapper
public interface OrderStatusMapper extends BaseMapper<OrderStatusInfo> {

}
package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细信息 数据层
 *
 * @author lgy
 * @date 2019-12-13
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}
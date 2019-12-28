package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderPayInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单支付信息 数据层
 *
 * @author lgy
 * @date 2019-12-13
 */
@Mapper
public interface OrderPayInfoMapper extends BaseMapper<OrderPayInfo> {

}
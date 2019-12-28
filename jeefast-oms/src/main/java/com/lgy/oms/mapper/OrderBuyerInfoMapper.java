package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderBuyerInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单买家信息 数据层
 *
 * @author lgy
 * @date 2019-12-13
 */
@Mapper
public interface OrderBuyerInfoMapper extends BaseMapper<OrderBuyerInfo> {

}
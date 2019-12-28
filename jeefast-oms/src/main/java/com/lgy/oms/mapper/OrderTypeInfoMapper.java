package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderTypeInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单类型信息 数据层
 *
 * @author lgy
 * @date 2019-12-13
 */
@Mapper
public interface OrderTypeInfoMapper extends BaseMapper<OrderTypeInfo> {

}
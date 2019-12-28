package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderMain;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单审核信息 数据层
 *
 * @author lgy
 * @date 2019-11-25
 */
@Mapper
public interface OrderMainMapper extends BaseMapper<OrderMain> {

}
package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.StandardOrderData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 交易订单快照 数据层
 *
 * @author lgy
 * @date 2019-12-03
 */
@Mapper
public interface TradeStandardMapper extends BaseMapper<StandardOrderData> {

}
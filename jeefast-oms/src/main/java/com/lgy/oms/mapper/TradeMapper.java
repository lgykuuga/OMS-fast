package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易订单 数据层
 *
 * @author lgy
 * @date 2019-10-15
 */
@Mapper
public interface TradeMapper extends BaseMapper<Trade> {

    /**
     * 判断订单是否存在
     *
     * @param tid   平台交易单号
     * @param shop  店铺
     * @param valid 是否查询有效订单
     * @return
     */
    List<Trade> checkOrderExist(@Param("tid")String tid, @Param("shop")String shop,  @Param("valid")boolean valid);
}
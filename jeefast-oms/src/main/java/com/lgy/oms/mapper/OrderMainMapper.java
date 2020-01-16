package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.domain.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单审核信息 数据层
 *
 * @author lgy
 * @date 2019-11-25
 */
public interface OrderMainMapper extends BaseMapper<OrderMain> {

    /**
     * 查询订单
     * @param orderVO
     * @return
     */
    List<OrderVO> queryOrderList(@Param("order")OrderVO orderVO);
}
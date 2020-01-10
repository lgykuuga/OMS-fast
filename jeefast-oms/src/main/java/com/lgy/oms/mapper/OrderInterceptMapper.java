package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 订单拦截信息 数据层
 *
 * @author lgy
 * @date 2019-12-13
 */
public interface OrderInterceptMapper extends BaseMapper<OrderInterceptInfo> {

    /**
     * 新增或更新拦截订单信息
     *
     * @param orderId 订单号
     * @param type    拦截类型
     * @param content 拦截内容
     * @return
     */
    Integer addOrUpdateOrderIntercept(@Param("orderId")String orderId, @Param("type")Integer type,
                                      @Param("content")String content);
}
package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.TraceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单轨迹服务 数据层
 *
 * @author lgy
 * @date 2019-12-26
 */
public interface TraceLogMapper extends BaseMapper<TraceLog> {

    /**
     * 增加订单操作记录
     *
     * @param entity
     */
    void add(TraceLog entity);

    /**
     * 批量增加订单操作记录---建议单批次不要超过 100条
     *
     * @param list
     */
    void batchAdd(List<TraceLog> list);

    /**
     * 查询单张订单操作记录
     *
     * @param orderId 订单
     * @return
     */

    List<TraceLog> getTraceByOrderId(@Param("order_id") String orderId);

    /**
     * 查询单张订单操作记录
     *
     * @param orderId 订单
     * @param module  模块号
     * @return
     */
    List<TraceLog> getTraceByOrderIdAndModule(@Param("order_id") String orderId,
                                              @Param("module") String module);

    /**
     * 根据日志等级查询单张订单操作记录
     *
     * @param orderId 订单号
     * @param level   日志等级(不传为查询所有等级)
     * @return
     */
    List<TraceLog> getTraceByOrderAndLevel(@Param("order_id") String orderId,
                                           @Param("level") Integer level);
}
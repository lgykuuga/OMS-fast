package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.TraceLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单轨迹日志 服务层
 *
 * @author lgy
 * @date 2019-12-26
 */
public interface ITraceLogService extends IService<TraceLog> {

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
     */
    List<TraceLog> get(String orderId);

    /**
     * 查询单张订单操作记录
     *
     * @param orderId 订单
     * @param module  模块号
     */
    List<TraceLog> get(String orderId, String module);

    /**
     * 根据日志等级查询单张订单操作记录
     *
     * @param orderId 订单号
     * @param level   日志等级(不传为查询所有等级)
     * @return
     */
    List<TraceLog> get(String orderId, Integer level);
}
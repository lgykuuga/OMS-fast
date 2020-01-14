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
     * 查询订单操作记录
     *
     * @param traceLog 订单
     */
    List<TraceLog> get(TraceLog traceLog);

}
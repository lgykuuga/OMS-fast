package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.mapper.TraceLogMapper;
import com.lgy.oms.service.ITraceLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单轨迹服务 服务层实现
 *
 * @author lgy
 * @date 2019-12-26
 */
@Service
public class TraceLogServiceImpl extends ServiceImpl<TraceLogMapper, TraceLog> implements ITraceLogService {

    private static Logger logger = LoggerFactory.getLogger(TraceLogServiceImpl.class);

    @Autowired
    TraceLogMapper traceLogMapper;

    @Override
    public void add(TraceLog entity) {
        if (StringUtils.isEmpty(entity.getOrderId())) {
            throw new NullPointerException("订单号不能为空!");
        }
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(DateUtils.getNowDate());
        }
        //TODO Disruptor并发框架
        traceLogMapper.add(entity);
    }

    @Override
    public void batchAdd(List<TraceLog> list) {
        //TODO Disruptor并发框架
        for (TraceLog traceLog : list) {

            traceLogMapper.add(traceLog);
        }
    }

    @Override
    public List<TraceLog> get(String orderId) {
        return traceLogMapper.getTraceByOrderId(orderId);
    }

    @Override
    public List<TraceLog> get(String orderId, String module) {
        return traceLogMapper.getTraceByOrderIdAndModule(orderId, module);
    }

    @Override
    public List<TraceLog> get(String orderId, Integer level) {
        return traceLogMapper.getTraceByOrderAndLevel(orderId, level);
    }

}
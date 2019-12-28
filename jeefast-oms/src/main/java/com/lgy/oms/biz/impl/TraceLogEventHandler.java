package com.lgy.oms.biz.impl;

import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.mapper.TraceLogMapper;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 订单日志轨迹服务事件消费者
 * @Author LGy
 * @Date 2019/12/27 10:36
 **/
@Service
public class TraceLogEventHandler implements EventHandler<TraceLog> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TraceLogMapper traceLogMapper;

    @Override
    public void onEvent(TraceLog traceLog, long l, boolean b) throws Exception {
        logger.info("订单轨迹日志成功消费[{}]", traceLog.toString());
        traceLogMapper.add(traceLog);
    }
}

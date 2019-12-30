package com.lgy.oms.disruptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 调用轨迹日志Disruptor
 * @Author LGy
 * @Date 2019/12/30
 */
@Service
public class TraceLogApi {

    @Autowired
    DisruptorTraceLogUtil disruptorTraceLogUtil;

    public void addTraceLogAction(TraceLogEvent traceLogEvent) {
        disruptorTraceLogUtil
                .getProducter()
                .onData(traceLogEvent);
    }
}

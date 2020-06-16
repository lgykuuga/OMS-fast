package com.lgy.oms.disruptor.tracelog;

import com.lgy.oms.domain.TraceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description 调用轨迹日志Disruptor
 * @Author LGy
 * @Date 2019/12/30
 */
@Service
public class TraceLogApi {


    @Autowired
    TraceLogDisruptorUtil traceLogDisruptorUtil;

    public void addTraceLogAction(TraceLog traceLog) {

        TraceLogEvent event = new TraceLogEvent();
        event.setTraceLog(traceLog);

        //轨迹记录级别
        traceLogDisruptorUtil
                .getProducer()
                .onData(event);
    }
}

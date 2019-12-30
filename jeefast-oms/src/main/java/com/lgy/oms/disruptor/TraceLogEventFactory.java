package com.lgy.oms.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @Author LGy
 * @Date 2019/12/30
 * @Description
 */
public class TraceLogEventFactory implements EventFactory<TraceLogEvent> {

    @Override
    public TraceLogEvent newInstance() {
        return new TraceLogEvent();
    }
}

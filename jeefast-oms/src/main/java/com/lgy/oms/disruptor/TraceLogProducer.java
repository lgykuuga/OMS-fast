package com.lgy.oms.disruptor;

import com.lgy.oms.domain.TraceLog;
import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description 内容生产者
 * @Author LGy
 * @Date 2019/12/27
 */
public class TraceLogProducer {

    private static Logger logger = LoggerFactory.getLogger(TraceLogProducer.class);

    private final RingBuffer<TraceLogEvent> ringBuffer;

    public TraceLogProducer(RingBuffer<TraceLogEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    protected void sendTraceLog(TraceLog traceLog, TraceLogHandlerCallBack callBack) {
        // 获取下一个序号
        long sequence = ringBuffer.next();
        try {
            // 写入数据
            TraceLogEvent traceLogEvent = ringBuffer.get(sequence);
            traceLogEvent.setTraceLog(traceLog);
            traceLogEvent.setHandlerCallBack(callBack);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            logger.info("开始生产订单轨迹[{}]", traceLog.toString());
            // 通知消费者该资源可以消费了
            ringBuffer.publish(sequence);
        }
    }

    protected void sendTraceLog(TraceLog traceLog) {
        sendTraceLog(traceLog, null);
    }

}
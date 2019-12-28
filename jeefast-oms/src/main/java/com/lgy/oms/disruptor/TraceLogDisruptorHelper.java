package com.lgy.oms.disruptor;

import com.lgy.oms.domain.TraceLog;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import javax.annotation.PostConstruct;

/**
 * @Description 轨迹日志Disruptor帮助类
 * @Author LGy
 * @Date 2019/12/27
 */
public class TraceLogDisruptorHelper {

    private int handlerCount = 1;
    private int bufferSize = 1024;
    private Disruptor<TraceLogEvent> disruptor;
    private TraceLogProducer traceLogProducer;

    public TraceLogDisruptorHelper() {
    }

    public TraceLogDisruptorHelper(int handlerCount, int bufferSize) {
        this.handlerCount = handlerCount;
        this.bufferSize = bufferSize;
    }

    @PostConstruct
    private void start() {

        // Construct the Disruptor
        disruptor = new Disruptor<>(TraceLogEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(new TraceLogHandler());

        // Start the Disruptor
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<TraceLogEvent> ringBuffer = disruptor.getRingBuffer();
        traceLogProducer = new TraceLogProducer(ringBuffer);
    }

    public void sendTraceLog(TraceLog traceLog) {
        traceLogProducer.sendTraceLog(traceLog);
    }

    /**
     * 停止
     */
    public void shutdown() {
        doHalt();
    }

    private void doHalt() {
        disruptor.halt();
    }

}

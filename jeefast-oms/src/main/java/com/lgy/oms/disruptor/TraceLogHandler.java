package com.lgy.oms.disruptor;

import com.lgy.oms.service.ITraceLogService;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Description 内容消费者
 * @Author LGy
 * @Date 2019/12/27
 */
@Service
public class TraceLogHandler implements EventHandler<TraceLogEvent> {

    private static Logger logger = LoggerFactory.getLogger(TraceLogHandler.class);

    /** 订单轨迹服务Service */
    @Resource
    ITraceLogService traceLogService;

    @Override
    public void onEvent(TraceLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        logger.info("开始消费订单轨迹[{}]", event.getTraceLog().toString());
        traceLogService.add(event.getTraceLog());
    }


}
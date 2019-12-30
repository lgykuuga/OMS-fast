package com.lgy.oms.disruptor;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.service.ITraceLogService;
import com.lgy.system.domain.SysUser;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @Description 内容消费者
 * @Author LGy
 * @Date 2019/12/27
 */
@Component
public class TraceLogHandler implements EventHandler<TraceLogEvent>, WorkHandler<TraceLogEvent> {

    private static Logger logger = LoggerFactory.getLogger(TraceLogHandler.class);

    /**
     * 订单轨迹服务Service
     */
    @Autowired
    ITraceLogService traceLogService;

    @Override

    public void onEvent(TraceLogEvent event, long sequence, boolean endOfBatch) {
        logger.info("开始消费订单轨迹EventHandler[{}]", event.getTraceLog().toString());
        onEvent(event);
    }


    @Override
    public void onEvent(TraceLogEvent event) {
        logger.info("开始消费订单轨迹WorkHandler[{}]", event.getTraceLog().toString());
        traceLogService.add(event.getTraceLog());
    }
}
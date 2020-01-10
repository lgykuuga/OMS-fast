package com.lgy.oms.disruptor.tracelog;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.service.ITraceLogService;
import com.lgy.system.domain.SysUser;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
        //添加threadLocal中用户信息
        ShiroUtils.setUserThreadLocal(event.getSysUser());
        logger.info("开始消费订单轨迹EventHandler[{}]", event.getTraceLog().toString());
        onEvent(event);
        ShiroUtils.removeUserThreadLocal();
    }


    @Override
    public void onEvent(TraceLogEvent event) {
        traceLogService.add(event.getTraceLog());
    }
}
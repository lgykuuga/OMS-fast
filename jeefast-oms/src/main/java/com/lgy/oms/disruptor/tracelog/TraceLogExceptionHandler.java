package com.lgy.oms.disruptor.tracelog;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 轨迹日志异常处理
 * @Author LGy
 * @Date 2020/1/10 15:48
 **/
public class TraceLogExceptionHandler implements ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(TraceLogExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        ex.printStackTrace();
        logger.error("轨迹日志 disruptor error:sequence[{}],event[{}],ex[{}]",
                sequence, event.toString(), ex.getMessage());
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        logger.error("start 轨迹日志 disruptor error[{}]", ex.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        logger.error("shutdown 轨迹日志 disruptor error[{}]", ex.getMessage());
    }
}

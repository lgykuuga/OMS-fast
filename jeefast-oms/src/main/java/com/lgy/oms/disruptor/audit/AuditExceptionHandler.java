package com.lgy.oms.disruptor.audit;

import com.lmax.disruptor.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 审核订单异常处理
 * @Author LGy
 * @Date 2020/1/10 15:48
 **/
public class AuditExceptionHandler implements ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(AuditExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
        ex.printStackTrace();
        logger.error("审核订单 disruptor error:sequence[{}],event[{}],ex[{}]",
                sequence, event.toString(), ex.getMessage());
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        logger.error("start 审核订单 disruptor error[{}]", ex.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        logger.error("shutdown 审核订单 disruptor error[{}]", ex.getMessage());
    }
}

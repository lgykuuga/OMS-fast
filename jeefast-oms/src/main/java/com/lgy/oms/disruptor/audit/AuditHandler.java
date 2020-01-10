package com.lgy.oms.disruptor.audit;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.IAuditOrderService;
import com.lgy.oms.disruptor.tracelog.TraceLogEvent;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.service.ITraceLogService;
import com.lgy.system.domain.SysUser;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Description 审单内容消费者
 * @Author LGy
 * @Date 2019/12/27
 */
@Component
public class AuditHandler implements EventHandler<OrderNumberEvent>, WorkHandler<OrderNumberEvent> {

    private static Logger logger = LoggerFactory.getLogger(AuditHandler.class);

    /**
     * 订单轨迹服务Service
     */
    @Autowired
    IAuditOrderService auditOrderService;

    @Override
    public void onEvent(OrderNumberEvent event, long sequence, boolean endOfBatch) {
        //添加threadLocal中用户信息
        ShiroUtils.setUserThreadLocal(event.getSysUser());
        logger.debug("开始消费审单EventHandler[{}]", event.getOrderId());
        onEvent(event);
        ShiroUtils.removeUserThreadLocal();
    }


    @Override
    public void onEvent(OrderNumberEvent event) {
        auditOrderService.auditOrder(event.getOrderId(), new AuditParamDTO());
    }
}
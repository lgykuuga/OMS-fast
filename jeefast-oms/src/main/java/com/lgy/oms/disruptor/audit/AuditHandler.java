package com.lgy.oms.disruptor.audit;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.IAuditOrderService;
import com.lgy.oms.domain.dto.AuditParamDTO;
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
public class AuditHandler implements EventHandler<AuditOrderEvent>, WorkHandler<AuditOrderEvent> {

    private static Logger logger = LoggerFactory.getLogger(AuditHandler.class);

    /**
     * 订单轨迹服务Service
     */
    @Autowired
    IAuditOrderService auditOrderService;

    @Override
    public void onEvent(AuditOrderEvent event, long sequence, boolean endOfBatch) {
        //添加threadLocal中用户信息
        ShiroUtils.setUserThreadLocal(event.getSysUser());
        logger.debug("开始消费审单EventHandler[{}]", event.getOrderMain().getOrderId());
        onEvent(event);
        ShiroUtils.removeUserThreadLocal();
    }

    @Override
    public void onEvent(AuditOrderEvent event) {
        auditOrderService.start(event.getOrderMain(), event.getParam());
    }
}
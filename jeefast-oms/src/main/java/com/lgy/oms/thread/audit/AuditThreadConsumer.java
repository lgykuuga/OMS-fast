package com.lgy.oms.thread.audit;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.IAuditOrderService;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 审单Thread消费者
 * @Author LGy
 * @Date 2020/1/14 18:08
 **/
@Component
public class AuditThreadConsumer {

    private static Logger logger = LoggerFactory.getLogger(AuditThreadConsumer.class);

    @Autowired
    IAuditOrderService auditOrderService;

    public void executeOrderNumber(String orderId, AuditParamDTO param, SysUser sysUser) {
        ShiroUtils.setUserThreadLocal(sysUser);
        auditOrderService.auditOrder(orderId, param);
        logger.debug("审单Thread消费者消费订单[{}]成功", orderId);
    }

    public void executeOrder(OrderMain orderMain, AuditParamDTO param, SysUser sysUser) {
        ShiroUtils.setUserThreadLocal(sysUser);
        auditOrderService.start(orderMain, param);
        logger.debug("审单Thread消费者消费订单[{}]成功", orderMain.getOrderId());
    }


}

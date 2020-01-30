package com.lgy.oms.thread.distribution;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.IAuditOrderService;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.thread.audit.AuditThreadConsumer;
import com.lgy.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 配货Thread消费者
 * @Author LGy
  @Date 2020/1/14 18:08
 **/
@Component
public class DistributionThreadConsumer {

    private static Logger logger = LoggerFactory.getLogger(DistributionThreadConsumer.class);

    @Autowired
    IAuditOrderService auditOrderService;

    public void executeOrderNumber(String orderId, AuditParamDTO param, SysUser sysUser) {
        ShiroUtils.setUserThreadLocal(sysUser);
        auditOrderService.auditOrder(orderId, param);
        logger.debug("配货Thread消费者消费订单[{}]成功", orderId);
    }

    public void executeOrder(OrderMain orderMain, AuditParamDTO param, SysUser sysUser) {
        ShiroUtils.setUserThreadLocal(sysUser);
        auditOrderService.start(orderMain, param);
        logger.debug("配货Thread消费者消费订单[{}]成功", orderMain.getOrderId());
    }


}

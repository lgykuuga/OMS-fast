package com.lgy.oms.thread.audit;

import com.lgy.framework.util.ShiroUtils;
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
 * @Description 审单Thread生产者
 * @Author LGy
 * @Date 2020/1/14 18:08
 **/
@Component
public class AuditThreadProducer {

    private static Logger logger = LoggerFactory.getLogger(AuditThreadProducer.class);

    /**
     * 审核订单线程池
     */
    @Resource(name = "auditThreadPool")
    ThreadPoolTaskExecutor auditThreadPool;

    @Autowired
    AuditThreadConsumer auditThreadConsumer;

    public void executeOrderNumber(String orderId, AuditParamDTO param) {

        SysUser sysUser = ShiroUtils.getSysUser();

        auditThreadPool.execute(() -> {
            auditThreadConsumer.executeOrderNumber(orderId, param, sysUser);
        });

        logger.debug("审单Thread生产者生产订单[{}]成功", orderId);

    }

    public void executeOrder(OrderMain orderMain, AuditParamDTO param) {

        SysUser sysUser = ShiroUtils.getSysUser();

        auditThreadPool.execute(() -> {
            auditThreadConsumer.executeOrder(orderMain, param, sysUser);
        });

        logger.debug("审单Thread生产者生产订单[{}]成功", orderMain.getOrderId());

    }


}

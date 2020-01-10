package com.lgy.oms.disruptor.audit.sub;

import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.impl.audit.CheckOrderSpecial;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Description 校验订单信息拦截
 * @Author LGy
 * @Date 2020年1月8日
 */
@Component
public class CheckOrderSpecialHandler implements EventHandler<AuditOrderEvent>, WorkHandler<AuditOrderEvent> {

    private static Logger logger = LoggerFactory.getLogger(CheckOrderSpecialHandler.class);

    @Autowired
    CheckOrderSpecial checkOrderSpecial;

    @Override
    public void onEvent(AuditOrderEvent event, long sequence, boolean endOfBatch) {
        //添加threadLocal中用户信息
        ShiroUtils.setUserThreadLocal(event.getSysUser());
        logger.debug("开始消费审单[{}]校验地址有效性", event.getOrderMain().getOrderId());
        onEvent(event);
        ShiroUtils.removeUserThreadLocal();
    }


    @Override
    public void onEvent(AuditOrderEvent event) {
        checkOrderSpecial.execute(event);
    }
}
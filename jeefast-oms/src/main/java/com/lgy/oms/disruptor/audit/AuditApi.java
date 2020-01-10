package com.lgy.oms.disruptor.audit;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.disruptor.audit.sub.AuditOrderEvent;
import com.lgy.oms.disruptor.audit.sub.AuditSubDisruptorUtil;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description 调用审单Disruptor
 * @Author LGy
 * @Date 2020/1/7
 */
@Service
public class AuditApi {

    @Autowired
    AuditDisruptorUtil auditDisruptorUtil;

    @Autowired
    AuditSubDisruptorUtil auditSubDisruptorUtil;

    public CommonResponse<String> addAuditAction(String orderId) {

        OrderNumberEvent event = new OrderNumberEvent();
        event.setOrderId(orderId);

        auditDisruptorUtil.getProducer().onData(event);

        return new CommonResponse<String>().ok("下发成功");
    }

    public CommonResponse<String> addAuditSubAction (
            OrderMain orderMain, StrategyAudit auditStrategy, AuditParamDTO param) {

        AuditOrderEvent event = new AuditOrderEvent();
        event.setOrderMain(orderMain);
        event.setAuditStrategy(auditStrategy);
        event.setParam(param);
        auditSubDisruptorUtil.getProducer().onData(event);

        return new CommonResponse<String>().ok("下发成功");
    }

}

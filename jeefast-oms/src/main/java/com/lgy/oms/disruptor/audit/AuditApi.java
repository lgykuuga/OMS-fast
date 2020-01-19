package com.lgy.oms.disruptor.audit;

import com.lgy.common.core.domain.CommonResponse;
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

    /**
     * 审核订单流程,开始审核订单流程
     *
     * @param orderMain
     * @param param
     * @return
     */
    public CommonResponse<String> addAuditAction(OrderMain orderMain, AuditParamDTO param) {

        AuditOrderEvent event = new AuditOrderEvent();
        event.setOrderMain(orderMain);
        event.setParam(param);
        auditDisruptorUtil.getProducer().onData(event);
        return new CommonResponse<String>().ok("下发成功");
    }

    /**
     * 审核订单内部流程,用于检验地址,校验订单信息
     *
     * @param orderMain
     * @param auditStrategy
     * @param param
     * @return
     */
    public CommonResponse<String> addAuditSubAction(
            OrderMain orderMain, StrategyAudit auditStrategy, AuditParamDTO param) {

        AuditOrderEvent event = new AuditOrderEvent();
        event.setOrderMain(orderMain);
        event.setAuditStrategy(auditStrategy);
        event.setParam(param);
        auditSubDisruptorUtil.getProducer().onData(event);

        return new CommonResponse<String>().ok("下发成功");
    }

}

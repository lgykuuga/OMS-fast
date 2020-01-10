package com.lgy.oms.biz.impl.audit;

import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.disruptor.audit.sub.AuditOrderEvent;
import com.lgy.oms.disruptor.audit.sub.CheckAddressHandler;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.order.OrderMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 校验订单地址有效性
 * @Author LGy
 * @Date 2020/1/8 17:35
 **/
@Service
public class CheckAddress {

    private static Logger logger = LoggerFactory.getLogger(CheckAddress.class);

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    public void execute(AuditOrderEvent event) {
        StrategyAudit auditStrategy = event.getAuditStrategy();
        if (Constants.ON.equals(auditStrategy.getAddressValid())) {
            //开启地址校验
            //TODO 调用 百度地图开放平台API 校验订单地址有效性


        } else {
            logger.debug("订单[{}]店铺策略[{}]未开启校验地址有效性,不校验地址",
                    event.getOrderMain().getOrderId(), auditStrategy.getGco());
        }
    }


}

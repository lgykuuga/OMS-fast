package com.lgy.oms.biz.impl.audit;

import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.audit.sub.AuditOrderEvent;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.enums.strategy.ProcessEnum;
import com.lgy.oms.service.IStrategyConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 发送订单至待配货队列
 * @Author LGy
 * @Date 2020/1/8 18:04
 **/
@Service
public class SendOrderInfo2Distribution {

    private static Logger logger = LoggerFactory.getLogger(SendOrderInfo2Distribution.class);

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;
    /**
     * 转单策略
     */
    @Autowired
    IStrategyConvertService strategyConvertService;


    /**
     * 下发至配货队列条件:
     * 0.转单策略流转方式为状态机触发
     * 1.完成审核通过
     * 2.请求参数设置不参与合并
     * 3.店铺设置配置不参与合并
     * (请求参数设置优先级大于店铺配置)
     *
     * @param event
     */
    public void execute(AuditOrderEvent event) {

        if (event.getParam().getMerge()) {
            //审核设置参与合并,加入订单池等待合并,不下发至配货队列
            logger.debug("单据[{}]设置参与合并,加入订单池等待合并,不下发至配货队列", event.getOrderMain().getOrderId());
            return;
        }

        //TODO 合并策略

        //转单策略店铺
        StrategyConvert strategyConvert = strategyConvertService.getStrategyByShop(event.getOrderMain().getShop());
        //异常信息记录
        StringBuilder sb = new StringBuilder();

        if (strategyConvert == null) {
            sb.append("店铺").append(event.getOrderMain().getShop()).append("未设置转单策略,审核完成不能自动下发订单");
            //保存轨迹
            traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDERMAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.SET_ABNORMAL.getValue(), TraceLevelType.ABNORMAL.getKey(), sb.toString()));

            logger.warn("单据[{}][{}]", event.getOrderMain().getOrderId(), sb.toString());
        }

        if (ProcessEnum.STATE_MACHINE.getKey().equals(strategyConvert.getProcess())) {
            //状态机触发
            logger.debug("单据[{}]下发至配货队列", event.getOrderMain().getOrderId());

        } else {
            logger.debug("单据[{}]转单策略设置流转方式为[{}],不自动下发订单",
                    event.getOrderMain().getOrderId(), strategyConvert.getProcess());
        }

    }

}

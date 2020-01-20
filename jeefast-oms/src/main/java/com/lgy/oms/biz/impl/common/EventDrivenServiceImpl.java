package com.lgy.oms.biz.impl.common;

import com.lgy.common.constant.Constants;
import com.lgy.oms.biz.IEventDrivenService;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.strategy.ProcessEnum;
import com.lgy.oms.service.IStrategyConvertService;
import com.lgy.oms.thread.audit.AuditThreadProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description 事件驱动Service实现,
 * 即完成当前动作,判断下一步动作应该执行什么流程
 * @Author LGy
 * @Date 2020/1/19 11:35
 **/
@Service
public class EventDrivenServiceImpl implements IEventDrivenService {

    private static Logger logger = LoggerFactory.getLogger(EventDrivenServiceImpl.class);

    /**
     * 转单策略
     */
    @Autowired
    IStrategyConvertService strategyConvertService;

    /**
     * 审单线程生产者
     */
    @Autowired
    AuditThreadProducer auditThreadProducer;

    @Value("${lgy.rabbitMQ}")
    private String rabbitMQ;

    @Override
    public void finishConvert(OrderMain orderMain, StrategyConvert strategy) {

        if (orderMain.getOrderInterceptInfo() == null) {
            if (ProcessEnum.STATE_MACHINE.getKey().equals(strategy.getProcess())) {

                logger.debug("单据[{}]转单完成且无异常拦截,店铺策略事件驱动为状态机触发,开始自动审单",
                        orderMain.getOrderId());

                if (Constants.ON.equals(rabbitMQ)) {
                    //开启rabbitMQ,则加入rabbitMQ队列

                } else {
                    AuditParamDTO param = new AuditParamDTO();
                    //对象已装配完成,不自动装配,加入线程池
                    param.setInstall(false);
                    auditThreadProducer.executeOrder(orderMain, param);
                }

            }
        } else {
            logger.warn("单据[{}]转单完成且存在异常,拦截", orderMain.getOrderId());
        }
    }

    @Override
    public void finishAudit(OrderMain orderMain, AuditParamDTO param) {

        if (param.getMerge()
                || param.getDelayDistributionTime() != null) {
            //设置参与合并,则不直接下发至配货队列
            //设置延迟配货,则不直接下发至配货队列
            return;
        }

        StrategyConvert strategy = strategyConvertService.getStrategyByShop(orderMain.getShop());
        if (strategy != null && ProcessEnum.STATE_MACHINE.getKey().equals(strategy.getProcess())) {
            //转单策略 事件驱动类型为状态机触发
            logger.debug("单据[{}]审单完成且无异常拦截,店铺策略事件驱动为状态机触发/不参与合并/未设置延迟配货,开始自动配货",
                    orderMain.getOrderId());
            //TODO
        }


    }
}

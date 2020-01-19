package com.lgy.oms.biz;


import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderMain;

/**
 * @Description 事件驱动Service
 * @Author LGy
 * @Date 2019/10/15
 */
public interface IEventDrivenService {


    /**
     * 完成转单流程,交由事件驱动判断下一步动作
     *
     * @param orderMain 订单主信息
     * @param strategy  转单策略
     */
    void finishConvert(OrderMain orderMain, StrategyConvert strategy);

    /**
     * 完成审单流程,交由事件驱动判断下一步动作
     *
     * @param
     * @param orderMain 订单主信息
     * @param param     审核订单参数
     */
    void finishAudit(OrderMain orderMain, AuditParamDTO param);
}

package com.lgy.oms.biz.impl.distribution;

import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.IPreDistributionService;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.factory.TraceLogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 预分配配货服务实现
 * @Author LGy
 * @Date 2020/2/20
 */
@Service
public class PreDistributionServiceImpl implements IPreDistributionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;


    @Override
    public CommonResponse<String> start(OrderMain orderMain, StrategyDistribution strategyDistribution, DistributionParamDTO param) {

        long startTime = System.currentTimeMillis();

        StringBuilder msg = new StringBuilder();

        //TODO

        long spendTime = System.currentTimeMillis() - startTime;
        msg.append("订单完成预分配流程,耗时:").append(spendTime).append("ms");


        //保存轨迹
        traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                OrderOperateType.SPLIT.getValue(), TraceLevelType.TRACE.getKey(), msg.toString()));
        logger.debug(msg.toString());

        return null;
    }


}

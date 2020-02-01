package com.lgy.oms.biz.impl.distribution;

import com.lgy.base.service.ICommodityService;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.IEventDrivenService;
import com.lgy.oms.biz.IFulfillService;
import com.lgy.oms.biz.IOrderConvertService;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.service.IOrderDetailService;
import com.lgy.oms.service.IOrderInterceptService;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IStrategyAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 履约订单服务实现
 * @Author LGy
 * @Date 2020/1/31
 */
@Service
public class FulfillServiceImpl implements IFulfillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    /**
     * 商品档案
     */
    @Autowired
    ICommodityService commodityService;
    /**
     * 主订单信息
     */
    @Autowired
    IOrderMainService orderMainService;
    /**
     * 订单明细信息
     */
    @Autowired
    IOrderDetailService orderDetailService;

    /**
     * 订单拦截信息
     */
    @Autowired
    IOrderInterceptService orderInterceptService;

    /**
     * 审单策略
     */
    @Autowired
    IStrategyAuditService strategyAuditService;

    /**
     * 事件驱动
     */
    @Autowired
    IEventDrivenService eventDrivenService;

    /**
     * 生成配货单Service
     */
    @Autowired
    IOrderConvertService orderConvertService;


    @Override
    public CommonResponse<String> fulfillOrder(String orderId, DistributionParamDTO param) {




        return null;
    }


    @Override
    public CommonResponse<String> start(OrderMain orderMain, DistributionParamDTO param) {

        //


        //生成配货单
        orderConvertService.execute(orderMain, param);

        return null;
    }


}

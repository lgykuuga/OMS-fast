package com.lgy.oms.biz.impl.trade;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.constant.ResponseCode;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.biz.*;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StandardOrderData;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.Trade;
import com.lgy.oms.domain.dto.TradeParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.TradeTranformStatusEnum;
import com.lgy.oms.factory.OrderMainFactory;
import com.lgy.oms.factory.TraceLogFactory;
import com.lgy.oms.interfaces.common.dto.standard.StandardOrder;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IStrategyConvertService;
import com.lgy.oms.service.ITradeService;
import com.lgy.oms.service.ITradeStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 交易订单转换成正式订单实现
 * @Author LGy
 * @Date 2019/12/2
 */
@Service
public class TradeConvertServiceImpl implements ITradeConvertService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 交易订单
     */
    @Autowired
    ITradeService tradeService;
    /**
     * 转单策略
     */
    @Autowired
    IStrategyConvertService strategyConvertService;
    /**
     * 订单主服务
     */
    @Autowired
    IOrderMainService orderMainService;
    /**
     * 创建主订单服务
     */
    @Autowired
    ICreateOrderMainService createOrderMainService;
    /**
     * 订单快照
     */
    @Autowired
    ITradeStandardService tradeStandardService;
    /**
     * 订单明细处理
     */
    @Autowired
    IOrderDetailProcessingService orderDetailProcessingService;
    /**
     * 订单统计处理
     */
    @Autowired
    IOrderStatisticsService orderStatisticsService;

    /**
     * 事件驱动
     */
    @Autowired
    IEventDrivenService eventDrivenService;

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    @Override
    public CommonResponse<String> execute(String tid, TradeParamDTO param) {

        // 查询表信息
        Trade trade = tradeService.getOne(new QueryWrapper<Trade>().lambda().eq(Trade::getTid, tid));

        //失败原因
        StringBuilder failReason = new StringBuilder();

        if (trade == null) {
            failReason.append(tid).append("单据信息不完整;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        if (TradeTranformStatusEnum.WAIT_TRANFORM.getValue() != trade.getFlag()) {
            failReason.append(tid).append("已转单;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        //订单池存在单号
        List<String> orderList = orderMainService.getOrderIdBySourceId(tid, true);
        if (StringUtils.isNotEmpty(orderList)) {
            //订单池存在单号
            String orderIds = StringUtils.join(orderList, Constants.COMMA);
            failReason.append(tid).append("无需再次转单,已存在有效单据：").append(orderIds).append(Constants.SEMICOLON);
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        StrategyConvert strategy = strategyConvertService.getStrategyByShop(trade.getShop());
        if (strategy == null) {
            failReason.append(tid).append("转单策略不存在;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        //订单报文信息
        StandardOrderData latestStandardOrderData = tradeStandardService.getLatestStandardOrderData(tid);
        if (latestStandardOrderData == null) {
            failReason.append(tid).append("单据找不到快照信息,请检查代码;");
            logger.error(failReason.toString());
            return new CommonResponse<String>().error(ResponseCode.ERROR, failReason.toString());
        }

        StandardOrder standardOrder = JSON.parseObject(latestStandardOrderData.getStandard(), StandardOrder.class);
        //转换成主订单信息
        OrderMain orderMain = OrderMainFactory.convert(standardOrder, strategy, param);

        //匹配商品编码
        orderDetailProcessingService.matchCommodity(orderMain, strategy);
        //解析组合商品
        orderDetailProcessingService.analysisCombCommodity(orderMain);
        //订单统计处理
        orderStatisticsService.orderStatisticsMethod(orderMain);
        //保存订单
        CommonResponse<OrderMain> saveOrderResp = createOrderMainService.saveOrder(orderMain);

        if (Constants.SUCCESS.equals(saveOrderResp.getCode())) {
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.CONVERT.getValue(), TraceLevelType.STATUS.getKey(), "订单新增保存"));

            //更新订单状态
            CommonResponse<String> response = tradeService.updateTranformStatus(trade);
            if (ResponseCode.SUCCESS.equals(response.getCode())) {

                //完成转单流程,交由事件驱动判断下一步动作
                eventDrivenService.finishConvert(orderMain, strategy);
            } else {

                //保存更新订单状态异常信息
                traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                        OrderOperateType.CONVERT.getValue(), TraceLevelType.ABNORMAL.getKey(), response.getMsg()));
            }

            return new CommonResponse<String>().ok("转单完成");
        }

        return new CommonResponse<String>().error(saveOrderResp.getCode(), saveOrderResp.getMsg());

    }

}

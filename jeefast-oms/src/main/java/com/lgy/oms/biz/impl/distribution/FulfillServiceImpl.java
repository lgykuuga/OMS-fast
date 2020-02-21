package com.lgy.oms.biz.impl.distribution;

import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.*;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * 配货策略
     */
    @Autowired
    IStrategyDistributionService strategyDistributionService;

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

    /**
     * 预分配流程
     */
    @Autowired
    IPreDistributionService preDistributionService;

    /**
     * 拆分订单逻辑
     */
    @Autowired
    ISplitOrderService splitOrderService;

    /**
     * 匹配仓库逻辑
     */
    @Autowired
    IMatchWarehouseService matchWarehouseService;

    /**
     * 订单锁定(占用)库存
     */
    @Autowired
    IOrderLockStockService orderLockStockService;

    /**
     * 更新订单信息
     */
    @Autowired
    IUpdateOrderFlagService updateOrderFlagService;


    @Override
    public CommonResponse<String> fulfillOrder(String orderId, DistributionParamDTO param) {




        return null;
    }


    @Override
    public CommonResponse<String> start(OrderMain orderMain, DistributionParamDTO param) {

        //开始时间,用于统计耗时
        long startTime = System.currentTimeMillis();
        param.setStartTime(startTime);

        logger.debug("开始订单配货[{}]", orderMain.getOrderId());

        //保存轨迹
        traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                OrderOperateType.DISTRIBUTION.getValue(), TraceLevelType.TRACE.getKey(), "开始订单配货:" + param.toString()));

        //店铺策略
        StrategyDistribution strategyDistribution = new StrategyDistribution();

        //1.组装配货策略
        conditionDistributionOrder(orderMain, strategyDistribution);

        //预分配
        CommonResponse<String> preResponse = preDistributionService.start(orderMain, strategyDistribution, param);
        if (!Constants.SUCCESS.equals(preResponse.getCode())) {
            return new CommonResponse<String>().ok(preResponse.getMsg());
        }

        //开始标准拆分订单(第一次拆分)
        CommonResponse<String> standardSplitResponse = splitOrderService.standardSplit(orderMain, strategyDistribution, param);
        if (!Constants.SUCCESS.equals(standardSplitResponse.getCode())) {
            return new CommonResponse<String>().ok(standardSplitResponse.getMsg());
        }

        //可用仓库列表
        List<String> warehouseList;
        //分配仓库
        CommonResponse<List<String>> matchWarehouseResponse = matchWarehouseService.start(orderMain, strategyDistribution, param);
        if (Constants.SUCCESS.equals(matchWarehouseResponse.getCode())) {
            warehouseList = matchWarehouseResponse.getData();
        } else {
            return new CommonResponse<String>().ok(matchWarehouseResponse.getMsg());
        }

        //锁库
        CommonResponse<DistributionOrder> lockStockResponse = orderLockStockService.execute(orderMain, strategyDistribution, param, warehouseList);
        if (!Constants.SUCCESS.equals(lockStockResponse.getCode())) {
            return new CommonResponse<String>().ok(lockStockResponse.getMsg());
        }

        //分配物流商

        //请求快递单号


        //生成配货单
        CommonResponse<DistributionOrder> orderConvertResponse = orderConvertService.execute(orderMain, param);
        if (!Constants.SUCCESS.equals(orderConvertResponse.getCode())) {
            return new CommonResponse<String>().error(orderConvertResponse.getCode(), orderConvertResponse.getMsg());
        }

        DistributionOrder distributionOrder = orderConvertResponse.getData();

        //更新订单状态
        updateOrderFlagService.distributionUpdateOrder(distributionOrder);


        return null;
    }

    private CommonResponse<OrderMain> conditionDistributionOrder(OrderMain orderMain, StrategyDistribution strategyDistribution) {

        //审单策略
        StrategyDistribution strategy = strategyDistributionService.getFullInfoStrategyByShop(orderMain.getShop());

        if (strategy == null) {
            logger.error("订单[{}]店铺[{}]未设置配货策略,不能配货;", orderMain.getOrderId(), orderMain.getShop());
            return new CommonResponse<OrderMain>().error(Constants.FAIL,
                    "店铺" + orderMain.getShop() + "未设置审单策略,不能审核订单;");
        } else {
            BeanUtils.copyProperties(strategy, strategyDistribution);
        }
        return new CommonResponse<OrderMain>().ok(orderMain);
    }


}

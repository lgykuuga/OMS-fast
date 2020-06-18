package com.lgy.oms.biz.impl.distribution;

import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.ISplitOrderService;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderSizeTypeEnum;
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
public class SplitOrderServiceImpl implements ISplitOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;


    @Override
    public CommonResponse<String> standardSplit(OrderMain orderMain, StrategyDistribution strategyDistribution, DistributionParamDTO param) {

        long startTime = System.currentTimeMillis();

        StringBuilder msg = new StringBuilder();

        //是否参与拆分流程标识
        boolean flag = true;
        //订单是否被拆分
        boolean split = false;

        if (OrderSizeTypeEnum.SINGLE_CARGO.getCode().equals(orderMain.getSizeType())) {
            //一单一货订单无法再拆分
            flag = false;
        }

        if (Constants.ON.equals(param.getSplit())) {
            //配货设置不拆分
            flag = false;
        }

        if (flag && Constants.ON.equals(param.getCategorySkuSplit()) && Constants.ON.equals(strategyDistribution.getCategorySkuSplit())) {
            //类别商品拆分
            //TODO
        }
        if (flag && Constants.ON.equals(param.getSpecialSkuSplit()) && Constants.ON.equals(strategyDistribution.getSpecialSkuSplit())) {
            //特殊商品拆分
            //TODO
        }
        if (flag && Constants.ON.equals(param.getStockSplit()) && Constants.ON.equals(strategyDistribution.getWarehouseSkuSplit())) {
            //仓库商品拆分
            //TODO
        }


        long spendTime = System.currentTimeMillis() - startTime;
        msg.append("订单完成第一步拆分流程,耗时:").append(spendTime).append("ms");

        //保存轨迹
        traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                OrderOperateType.DISTRIBUTION_PRE.getValue(), TraceLevelType.STATUS.getKey(), msg.toString()));
        logger.debug(orderMain.getOrderId() + msg.toString());

        if (split) {
            return new CommonResponse<String>().error(Constants.FAIL, "单据被拆分。");
        }

        return new CommonResponse<String>().ok("单据未参与类别商品拆分、特殊商品拆分、仓库商品拆分。");

    }


}

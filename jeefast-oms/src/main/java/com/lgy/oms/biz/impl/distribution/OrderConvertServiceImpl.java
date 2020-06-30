package com.lgy.oms.biz.impl.distribution;


import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.IOrderConvertService;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.distribution.DistributionDetail;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.factory.DistributionOrderFactory;
import com.lgy.oms.factory.TraceLogFactory;
import com.lgy.oms.service.IDistributionOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 审核订单转换成配货订单实现
 * @Author LGy
 * @Date 2020/1/31
 */
@Service
public class OrderConvertServiceImpl implements IOrderConvertService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDistributionOrderService distributionOrderService;

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    @Override
    public CommonResponse<DistributionOrder> execute(OrderMain orderMain, DistributionParamDTO param) {

        //转换生成配货单
        DistributionOrder distributionOrder = DistributionOrderFactory.convert(orderMain, param);
        //保存订单
        boolean b = distributionOrderService.saveOrder(distributionOrder);
        if (b) {
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.CREATE_DISTRIBUTION.getValue(), TraceLevelType.STATUS.getKey(),
                    "生成配货单成功,配货单号:" + distributionOrder.getDistributionId()));
        } else {
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.CREATE_DISTRIBUTION.getValue(), TraceLevelType.ABNORMAL.getKey(),
                    "生成配货单失败,请开发人员检查代码"));
            logger.error("单据[{}]生成配货单失败,请开发人员检查代码", orderMain.getOrderId());
            return new CommonResponse<DistributionOrder>().error(Constants.FAIL, "生成配货单失败,请开发人员检查代码");
        }
        return new CommonResponse<DistributionOrder>().ok(distributionOrder);
    }


}

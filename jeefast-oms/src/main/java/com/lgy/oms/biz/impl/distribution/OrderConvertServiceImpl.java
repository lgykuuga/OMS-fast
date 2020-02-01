package com.lgy.oms.biz.impl.distribution;


import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.IOrderConvertService;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.distribution.DistributionDetail;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
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
    public CommonResponse<String> execute(OrderMain orderMain, DistributionParamDTO param) {

        //转换生成配货单
        DistributionOrder distributionOrder = convert(orderMain, param);
        //保存订单
        boolean b = distributionOrderService.saveOrder(distributionOrder);
        if (b) {
            //保存轨迹服务
            traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.CREATE_DISTRIBUTION.getValue(), TraceLevelType.STATUS.getKey(),
                    "生成配货单成功,配货单号:" + distributionOrder.getDistributionId()));
        } else {
            //保存轨迹服务
            traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.CREATE_DISTRIBUTION.getValue(), TraceLevelType.ABNORMAL.getKey(),
                    "生成配货单失败,请开发人员检查代码"));
            logger.error("单据[{}]生成配货单失败,请开发人员检查代码", orderMain.getOrderId());
            return new CommonResponse<String>().error(Constants.FAIL, "生成配货单失败,请开发人员检查代码");
        }
        return new CommonResponse<String>().ok("生成配货单成功");
    }

    /**
     * 转换,生成配货单
     *
     * @param orderMain 主订单信息
     * @param param     配货参数
     * @return 配货单
     */
    private DistributionOrder convert(OrderMain orderMain, DistributionParamDTO param) {
        //配货单信息
        DistributionOrder distributionOrder = new DistributionOrder();
        BeanUtils.copyProperties(orderMain, distributionOrder);
        //生成配货单号
        String distributionId = createIdRule(orderMain);

        distributionOrder.setDistributionId(distributionId);

        List<OrderDetail> orderDetails = orderMain.getOrderDetails();

        List<DistributionDetail> distributionDetailList = new ArrayList<>(orderDetails.size());

        for (OrderDetail orderDetail : orderDetails) {
            DistributionDetail distributionDetail = new DistributionDetail();
            BeanUtils.copyProperties(orderDetail, distributionDetail);
            distributionDetail.setDistributionId(distributionId);
            distributionDetailList.add(distributionDetail);
        }

        distributionOrder.setDistributionDetail(distributionDetailList);
        return distributionOrder;
    }

    /**
     * 创建流水号ID规则
     *
     * @param orderMain 订单主信息
     * @return ID流水号
     */
    private String createIdRule(OrderMain orderMain) {

        /**
         * 配货单id = 订单Id - 生成配货单数量
         *例:
         *  OD20200131拆分成2个配货单
         *  OD20200131-0
         *  OD20200131-1
         *
         *  ID唯一性靠业务保证,无须使用redis缓存校验.
         */
        return orderMain.getOrderId()
                + "-"
                + orderMain.getDistributionQty();
    }
}

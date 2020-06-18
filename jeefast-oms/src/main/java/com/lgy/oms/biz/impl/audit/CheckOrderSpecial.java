package com.lgy.oms.biz.impl.audit;

import com.lgy.common.constant.Constants;
import com.lgy.common.utils.DateUtils;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.audit.AuditOrderEvent;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import com.lgy.oms.enums.order.OrderDetailRefundStatusEnum;
import com.lgy.oms.enums.order.OrderInterceptTypeEnum;
import com.lgy.oms.enums.strategy.AuditAmountEnum;
import com.lgy.oms.enums.strategy.AuditNumberEnum;
import com.lgy.oms.enums.strategy.AuditTimeEnum;
import com.lgy.oms.factory.TraceLogFactory;
import com.lgy.oms.service.IOrderMainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description 校验订单信息拦截
 * @Author LGy
 * @Date 2020/1/8 17:59
 **/
@Service
public class CheckOrderSpecial {

    private static Logger logger = LoggerFactory.getLogger(CheckOrderSpecial.class);

    @Autowired
    IOrderMainService orderMainService;
    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    public void execute(AuditOrderEvent event) {

        //拦截信息
        List<OrderInterceptInfo> orderInterceptList = event.getOrderInterceptInfoList();
        //审单策略
        StrategyAudit auditStrategy = event.getAuditStrategy();

        //记录拦截信息
        StringBuffer stringBuffer = new StringBuffer();

        //开启来源单号相同拦截
        if (Constants.ON.equals(auditStrategy.getSource().toString())) {
            checkSameOrderExist(event, orderInterceptList, stringBuffer);
        }
        //开启买家留言拦截
        if (Constants.ON.equals(auditStrategy.getBuyMessage().toString())) {
            checkBuyerMessage(event, orderInterceptList, stringBuffer);
        }
        //开启卖家留言拦截
        if (Constants.ON.equals(auditStrategy.getSellerMessage().toString())) {
            checkSellerMessage(event, orderInterceptList, stringBuffer);
        }
        //开启货到付款拦截
        if (Constants.ON.equals(auditStrategy.getCod().toString())) {
            checkCod(event, orderInterceptList, stringBuffer);
        }
        //开启卖家备注旗帜拦截
        if (Constants.ON.equals(auditStrategy.getSellerFlag().toString())) {
            checkSellerFlag(event, orderInterceptList, stringBuffer);
        }
        //开启退款拦截
        if (Constants.ON.equals(auditStrategy.getRefund().toString())) {
            checkRefund(event, orderInterceptList, stringBuffer);
        }
        //开启标记拦截
        if (Constants.NONE.equals(auditStrategy.getMark())) {
            checkMark(event, auditStrategy, orderInterceptList, stringBuffer);
        }
        //开启金额拦截
        if (!AuditAmountEnum.NOT_INTERCEPT.getCode().equals(auditStrategy.getAmount())) {
            checkAmount(event, auditStrategy, orderInterceptList, stringBuffer);
        }
        //开启时间范围拦截类型拦截
        if (!AuditTimeEnum.NOT_OPERATION.getCode().equals(auditStrategy.getTimeRange())) {
            checkTime(event, auditStrategy, orderInterceptList, stringBuffer);
        }
        //开启有效期类型拦截
        if (!AuditTimeEnum.NOT_OPERATION.getCode().equals(auditStrategy.getValidDateType())) {
            checkValidDate(event, auditStrategy, orderInterceptList, stringBuffer);
        }
        //开启数字拦截类型拦截
        if (!AuditNumberEnum.NOT_INTERCEPT.getCode().equals(auditStrategy.getNumber())) {
            checkNumber(event, auditStrategy, orderInterceptList, stringBuffer);
        }
        //开启毛利率拦截(毛利率设置为100则不拦截)
        if (!Constants.ONE_HUNDRED.equals(auditStrategy.getProfitValue())) {
            checkProfit(event, auditStrategy, orderInterceptList, stringBuffer);
        }
    }

    /**
     * 校验毛利率拦截
     */
    private void checkProfit(AuditOrderEvent event, StrategyAudit auditStrategy,
                             List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {
        //是否通过毛利率拦截
        boolean flag = true;

        BigDecimal orderAmount = event.getOrderMain().getOrderPayinfo().getOrderAmount();
        if (orderAmount.compareTo(new BigDecimal(auditStrategy.getProfitValue())) <= 0) {
            stringBuffer.append("订单金额").append(orderAmount)
                    .append("小于等于设置毛利率值:").append(auditStrategy.getProfitValue());
            flag = false;
        }

        if (!flag) {
            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.AMOUNT.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.AMOUNT.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }

    }

    /**
     * 校验数字拦截
     */
    private void checkNumber(AuditOrderEvent event, StrategyAudit auditStrategy,
                             List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {
        //是否通过校验数字拦截
        boolean flag = true;

        if (AuditNumberEnum.ORDER_QTY.getCode().equals(auditStrategy.getNumber())) {
            //订单商品总数量
            Integer qty = event.getOrderMain().getQty();

            //商品数量最小值
            if (qty.compareTo(auditStrategy.getNumberMin()) <= 0) {
                stringBuffer.append("订单商品数量").append(qty)
                        .append("小于等于订单商品数量拦截设置最小值:").append(auditStrategy.getNumberMin());
                flag = false;
            } else if (qty.compareTo(auditStrategy.getNumberMax()) >= 0) {
                stringBuffer.append("订单商品数量").append(qty)
                        .append("大于等于订单商品数量拦截设置最大值:").append(auditStrategy.getNumberMax());
                flag = false;
            }
        } else if (AuditNumberEnum.ORDER_WEIGHT.getCode().equals(auditStrategy.getNumber())) {
            //订单商品总重量
            BigDecimal weight = event.getOrderMain().getWeight();

            //重量拦截设置最小值
            if (weight.compareTo(new BigDecimal(auditStrategy.getNumberMin())) <= 0) {
                stringBuffer.append("订单重量").append(weight)
                        .append("小于等于订单重量拦截设置最小值:").append(auditStrategy.getNumberMin());
                flag = false;
            } else if (weight.compareTo(new BigDecimal(auditStrategy.getNumberMax())) >= 0) {
                stringBuffer.append("订单重量").append(weight)
                        .append("大于等于订单重量拦截设置最大值:").append(auditStrategy.getNumberMax());
                flag = false;
            }
        } else if (AuditNumberEnum.ORDER_VOLUME.getCode().equals(auditStrategy.getNumber())) {
            //订单商品总体积
            BigDecimal volume = event.getOrderMain().getVolume();

            //重量拦截设置最小值
            if (volume.compareTo(new BigDecimal(auditStrategy.getNumberMin())) <= 0) {
                stringBuffer.append("订单体积").append(volume)
                        .append("小于等于订单体积拦截设置最小值:").append(auditStrategy.getNumberMin());
                flag = false;
            } else if (volume.compareTo(new BigDecimal(auditStrategy.getNumberMax())) >= 0) {
                stringBuffer.append("订单体积").append(volume)
                        .append("大于等于订单体积拦截设置最大值:").append(auditStrategy.getNumberMax());
                flag = false;
            }
        }

        if (!flag) {
            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.NUMBER.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.NUMBER.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }


    }

    /**
     * 校验订单有效期拦截
     */
    private void checkValidDate(AuditOrderEvent event, StrategyAudit auditStrategy,
                                List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {
        //是否通过订单有效期拦截
        boolean flag = true;

        if (AuditTimeEnum.ORDER_PLACEMENT.getCode().equals(auditStrategy.getValidDateType())) {
            //订单下单时间
            String orderTime = event.getOrderMain().getOrderPayinfo().getOrderTime();

            //下单时间距今多少天
            long day = DateUtils.getDatePoor(DateUtils.parseDate(orderTime));

            if (day > auditStrategy.getValidDate()) {
                stringBuffer.append("下单时间").append(orderTime)
                        .append("距今已有").append(day)
                        .append("天,超过审单策略设置有效期(天):").append(auditStrategy.getValidDate())
                        .append(",拦截订单");
                flag = false;
            }
        } else if (AuditTimeEnum.ORDER_PAY.getCode().equals(auditStrategy.getValidDateType())) {
            //支付时间
            String payTime = event.getOrderMain().getOrderPayinfo().getPayTime();

            //支付时间距今多少天
            long day = DateUtils.getDatePoor(DateUtils.parseDate(payTime));

            if (day > auditStrategy.getValidDate()) {
                stringBuffer.append("支付时间").append(payTime)
                        .append("距今已有").append(day)
                        .append("天,超过审单策略设置有效期(天):").append(auditStrategy.getValidDate())
                        .append(",拦截订单");
                flag = false;
            }
        } else if (AuditTimeEnum.ORDER_CREATE.getCode().equals(auditStrategy.getValidDateType())) {
            //订单创建时间
            Date createTime = event.getOrderMain().getCreateTime();

            //订单创建距今多少天
            long day = DateUtils.getDatePoor(DateUtils.parseDate(createTime));

            if (day > auditStrategy.getValidDate()) {
                stringBuffer.append("订单创建时间").append(createTime)
                        .append("距今已有").append(day)
                        .append("天,超过审单策略设置有效期(天):").append(auditStrategy.getValidDate())
                        .append(",拦截订单");
                flag = false;
            }
        }

        if (!flag) {
            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.DATE.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.DATE.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }
    }

    /**
     * 校验时间拦截
     */
    private void checkTime(AuditOrderEvent event, StrategyAudit auditStrategy,
                           List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {
        //是否通过校验时间拦截
        boolean flag = true;

        //开始日期
        Date start = DateUtils.parseDate(auditStrategy.getTimeStart());
        //结束日期
        Date end = DateUtils.parseDate(auditStrategy.getTimeEnd());

        if (AuditTimeEnum.ORDER_PLACEMENT.getCode().equals(auditStrategy.getTimeRange())) {
            //订单下单时间
            String orderTime = event.getOrderMain().getOrderPayinfo().getOrderTime();

            if (DateUtils.parseDate(orderTime).after(start)
                    && (DateUtils.parseDate(orderTime).before(end))) {
                stringBuffer.append("下单时间").append(orderTime)
                        .append("在审单策略设置时间拦截范围").append(auditStrategy.getTimeStart())
                        .append("-").append(auditStrategy.getTimeEnd());
                flag = false;
            }
        } else if (AuditTimeEnum.ORDER_PAY.getCode().equals(auditStrategy.getTimeRange())) {
            //支付时间
            String payTime = event.getOrderMain().getOrderPayinfo().getPayTime();

            if (DateUtils.parseDate(payTime).after(start)
                    && (DateUtils.parseDate(payTime).before(end))) {
                stringBuffer.append("支付时间").append(payTime)
                        .append("在审单策略设置时间拦截范围").append(auditStrategy.getTimeStart())
                        .append("-").append(auditStrategy.getTimeEnd());
                flag = false;
            }
        } else if (AuditTimeEnum.ORDER_CREATE.getCode().equals(auditStrategy.getTimeRange())) {
            //订单创建时间
            Date createTime = event.getOrderMain().getCreateTime();

            if (createTime.after(start)
                    && (createTime.before(end))) {
                stringBuffer.append("支付时间").append(DateUtils.dateTime(createTime))
                        .append("在审单策略设置时间拦截范围").append(auditStrategy.getTimeStart())
                        .append("-").append(auditStrategy.getTimeEnd());
                flag = false;
            }
        }

        if (!flag) {
            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.DATE.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.DATE.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }

    }

    /**
     * 校验金额拦截
     */
    private void checkAmount(AuditOrderEvent event, StrategyAudit auditStrategy,
                             List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {
        //是否通过校验金额拦截
        boolean flag = true;

        if (AuditAmountEnum.ORDER_AMOUNT.getCode().equals(auditStrategy.getAmount())) {
            //订单金额
            BigDecimal orderAmount = event.getOrderMain().getOrderPayinfo().getOrderAmount();
            //订单金额最小值
            if (orderAmount.compareTo(auditStrategy.getAmountMin()) <= 0) {
                stringBuffer.append("订单金额").append(orderAmount)
                        .append("小于等于订单金额拦截设置最小值:").append(auditStrategy.getAmountMin());
                flag = false;
            } else if (orderAmount.compareTo(auditStrategy.getAmountMax()) >= 0) {
                stringBuffer.append("订单金额").append(orderAmount)
                        .append("大于等于订单金额拦截设置最大值:").append(auditStrategy.getAmountMax());
                flag = false;
            }
        } else if (AuditAmountEnum.PAY_AMOUNT.getCode().equals(auditStrategy.getAmount())) {
            //支付金额
            BigDecimal payAmount = event.getOrderMain().getOrderPayinfo().getPayAmount();
            //订单金额最小值
            if (payAmount.compareTo(auditStrategy.getAmountMin()) <= 0) {
                stringBuffer.append("支付金额").append(payAmount)
                        .append("小于等于订单金额拦截设置最小值:").append(auditStrategy.getAmountMin());
                flag = false;
            } else if (payAmount.compareTo(auditStrategy.getAmountMax()) >= 0) {
                stringBuffer.append("支付金额").append(payAmount)
                        .append("大于等于订单金额拦截设置最大值:").append(auditStrategy.getAmountMax());
                flag = false;
            }
        }

        if (!flag) {
            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.AMOUNT.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.AMOUNT.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }

    }

    /**
     * 校验标记拦截
     */
    private void checkMark(AuditOrderEvent event, StrategyAudit auditStrategy,
                           List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {

        //订单标记数组
        String[] markArray = event.getOrderMain().getMark().split(Constants.COMMA);
        //策略设置拦截标记
        String marks = auditStrategy.getMark();

        for (String mark : markArray) {
            if (marks.contains(mark)) {
                stringBuffer.append("存在标记拦截:").append(mark);
            }
        }

        logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

        //非强制审核,则拦截
        if (!event.getParam().getEnforce()) {
            orderInterceptList.add(new OrderInterceptInfo(
                    event.getOrderMain().getOrderId(),
                    OrderInterceptTypeEnum.SELLER_FLAG.getCode(),
                    stringBuffer.toString()));
        }
        //保存轨迹服务
        traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                OrderOperateType.SELLER_FLAG.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

        stringBuffer.delete(0, stringBuffer.length());
    }

    /**
     * 校验退款拦截
     */
    private void checkRefund(AuditOrderEvent event, List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {

        //是否存在退款
        boolean flag = false;
        if (Constants.YES.equals(event.getOrderMain().getRefund())) {
            flag = true;
            stringBuffer.append("存在退款标识");
        } else {
            List<OrderDetail> orderDetails = event.getOrderMain().getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                if (!OrderDetailRefundStatusEnum.NO_REFUND.getCode()
                        .equals(orderDetail.getRefundStatus())) {
                    flag = true;
                    stringBuffer.append("明细商品").append(orderDetail.getCommodity())
                            .append("存在退款");
                }
            }
        }

        if (flag) {
            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());
            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.REFUND.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.REFUND.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }
    }

    /**
     * 校验卖家备注旗帜
     */
    private void checkSellerFlag(AuditOrderEvent event, List<OrderInterceptInfo> orderInterceptList, StringBuffer stringBuffer) {
        if (Constants.YES.equals(event.getOrderMain().getSellerFlag())) {

            stringBuffer.append("卖家备注旗帜拦截");

            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.SELLER_FLAG.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.SELLER_FLAG.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }
    }

    /**
     * 校验货到付款
     */
    private void checkCod(AuditOrderEvent event, List<OrderInterceptInfo> orderInterceptList,
                          StringBuffer stringBuffer) {
        if (Constants.YES.equals(event.getOrderMain().getOrderTypeinfo().getCod().toString())) {

            stringBuffer.append("货到付款拦截");

            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.COD.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.COD.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }
    }

    /**
     * 校验卖家留言
     */
    private void checkSellerMessage(AuditOrderEvent event, List<OrderInterceptInfo> orderInterceptList,
                                    StringBuffer stringBuffer) {
        if (StringUtils.isNotEmpty(event.getOrderMain().getOrderBuyerinfo().getSellerMessage())) {

            stringBuffer.append("卖家留言拦截:").append(event.getOrderMain().getOrderBuyerinfo().getBuyerMessage());

            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.SELLER_MESSAGE.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.SELLER_MESSAGE.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }
    }

    /**
     * 校验买家留言
     */
    private void checkBuyerMessage(AuditOrderEvent event, List<OrderInterceptInfo> orderInterceptList,
                                   StringBuffer stringBuffer) {
        if (StringUtils.isNotEmpty(event.getOrderMain().getOrderBuyerinfo().getBuyerMessage())) {

            stringBuffer.append("买家留言拦截:").append(event.getOrderMain().getOrderBuyerinfo().getBuyerMessage());

            logger.debug("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.BUYER_MESSAGE.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.BUYER_MESSAGE.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }
    }

    /**
     * 校验是否存在相同来源单号的有效单据
     */
    private void checkSameOrderExist(AuditOrderEvent event, List<OrderInterceptInfo> orderInterceptList,
                                     StringBuffer stringBuffer) {
        List<String> orderIds = orderMainService.checkSameOrderExist(event.getOrderMain().getOrderId(),
                event.getOrderMain().getSourceId(), event.getOrderMain().getOwner(),
                event.getOrderMain().getOrderStatusinfo().getStatus());
        if (!orderIds.isEmpty()) {

            stringBuffer.append("来源单号").append(event.getOrderMain().getSourceId())
                    .append("存在有效的相同订单:").append(orderIds.toString());

            logger.warn("单据[{}][{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                orderInterceptList.add(new OrderInterceptInfo(
                        event.getOrderMain().getOrderId(),
                        OrderInterceptTypeEnum.SAME_RESOURCE.getCode(),
                        stringBuffer.toString()));
            }
            //保存轨迹服务
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.AUDIT_CHECK_SAME_ORDER.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));

            stringBuffer.delete(0, stringBuffer.length());
        }

    }

}

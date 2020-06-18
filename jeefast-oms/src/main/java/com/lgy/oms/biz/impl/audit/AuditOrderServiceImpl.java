package com.lgy.oms.biz.impl.audit;

import com.lgy.base.domain.Commodity;
import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.framework.util.ShiroUtils;
import com.lgy.oms.biz.IAuditOrderService;
import com.lgy.oms.biz.IEventDrivenService;
import com.lgy.oms.biz.IUpdateOrderFlagService;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.audit.AuditApi;
import com.lgy.oms.disruptor.audit.AuditOrderEvent;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.dto.AuditParamDTO;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderDetailTypeEnum;
import com.lgy.oms.enums.order.OrderFlagEnum;
import com.lgy.oms.enums.order.OrderInterceptTypeEnum;
import com.lgy.oms.factory.TraceLogFactory;
import com.lgy.oms.service.IOrderDetailService;
import com.lgy.oms.service.IOrderInterceptService;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IStrategyAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 审核订单服务实现
 * @Author LGy
 * @Date 2020/1/7
 */
@Service
public class AuditOrderServiceImpl implements IAuditOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;
    /**
     * 处理订单业务逻辑
     */
    @Autowired
    AuditApi auditApi;
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
     * 更新订单状态
     */
    @Autowired
    IUpdateOrderFlagService updateOrderFlagService;

    /**
     * 事件驱动
     */
    @Autowired
    IEventDrivenService eventDrivenService;


    @Override
    public CommonResponse<String> auditOrder(String orderId, AuditParamDTO param) {

        if (StringUtils.isEmpty(orderId)) {
            logger.error("审核订单传入编码为空");
            return new CommonResponse<String>().error(Constants.FAIL, "审核订单传入编码为空");
        }

        //订单主信息
        OrderMain orderMain = new OrderMain();
        orderMain.setOrderId(orderId);

        //开始审单流程
        return start(orderMain, param);
    }

    @Override
    public CommonResponse<String> start(OrderMain orderMain, AuditParamDTO param) {

        //开始时间,用于统计耗时
        long startTime = System.currentTimeMillis();
        param.setStartTime(startTime);

        logger.debug("开始审核订单[{}]", orderMain.getOrderId());

        //保存轨迹
        traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                OrderOperateType.AUDIT.getValue(), TraceLevelType.TRACE.getKey(), "开始审核订单:" + param.toString()));

        //店铺策略
        StrategyAudit auditStrategy = new StrategyAudit();

        //订单是否通过审核
        boolean flag = true;
        //记录审核拦截原因
        StringBuilder failReason = new StringBuilder();

        //1.组装数据并判断审核条件
        CommonResponse<OrderMain> conditionAuditOrder = conditionAuditOrder(orderMain, auditStrategy, param);

        if (!Constants.SUCCESS.equals(conditionAuditOrder.getCode())) {
            flag = false;
            failReason.append(conditionAuditOrder.getMsg());
        }

        //2.校验订单有效性
        CommonResponse<String> checkOrderInfo = checkOrderInfo(orderMain);
        if (!Constants.SUCCESS.equals(checkOrderInfo.getCode())) {
            flag = false;
            failReason.append(checkOrderInfo.getMsg());
        }

        if (flag) {

            //有效性校验成功,进入下一步流程
            auditApi.addAuditSubAction(orderMain, auditStrategy, param);

        } else {
            //有效性校验失败,记录拦截信息
            orderInterceptService.addOrUpdateOrderIntercept(orderMain.getOrderId(),
                    OrderInterceptTypeEnum.VALIDITY_CHECK.getCode(), failReason.toString());
            //保存轨迹
            traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                    OrderOperateType.AUDIT.getValue(), TraceLevelType.ABNORMAL.getKey(), failReason.toString()));

            return new CommonResponse<String>().error(Constants.FAIL, failReason.toString());
        }

        return new CommonResponse<String>().ok("完成审核订单");
    }


    /**
     * 校验订单必填信息
     *
     * @param orderMain 订单主信息
     * @return 返回消息
     */
    private CommonResponse<String> checkOrderInfo(OrderMain orderMain) {

        //记录失败原因
        StringBuilder failReason = new StringBuilder();

        if (orderMain.getOrderBuyerinfo() == null) {
            failReason.append("订单买家信息为空;");
        }
        if (orderMain.getOrderPayinfo() == null) {
            failReason.append("订单支付信息为空;");
        }
        if (orderMain.getOrderTypeinfo() == null) {
            failReason.append("订单类型信息为空;");
        }
        if (orderMain.getOrderDetails() == null) {
            failReason.append("订单明细信息为空;");
        } else {
            for (OrderDetail orderDetail : orderMain.getOrderDetails()) {
                if (StringUtils.isEmpty(orderDetail.getCommodity())) {
                    failReason.append("订单明细存在商品编码为空;");
                    break;
                } else {

                    if (orderDetail.getQty() == null || orderDetail.getQty() == 0) {
                        failReason.append("订单明细商品").append(orderDetail.getCommodity()).append("數量为0;");
                        continue;
                    }

                    if (orderDetail.getType() == null || OrderDetailTypeEnum.UNPARSED.getCode().equals(orderDetail.getType())) {
                        failReason.append("订单存在未解析明细商品");
                        continue;
                    }

                    Commodity commodity = commodityService.getOne(orderDetail.getCommodity());
                    if (commodity == null) {
                        failReason.append("订单明细商品[").append(orderDetail.getCommodity()).append("]编码无效;");
                        continue;
                    }

                    //当订单商品明细是组合商品,判断组合商品下是否有组合商品明细
                    if (OrderDetailTypeEnum.COMB.getCode().equals(orderDetail.getType())) {
                        if (!orderDetailService.checkOrderCombDetailExist(orderDetail)) {
                            failReason.append("订单组合商品").append(orderDetail.getCommodity()).append("下无组合商品明细;");
                        }
                    }
                }
            }
        }

        if (StringUtils.isNotEmpty(failReason.toString())) {
            logger.error("单据[{}][{}]", orderMain.getOrderId(), failReason.toString());
            return new CommonResponse<String>().error(Constants.FAIL, failReason.toString());
        }

        //保存轨迹
        traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, orderMain.getOrderId(),
                OrderOperateType.AUDIT_CHECK.getValue(), TraceLevelType.TRACE.getKey(), "通过有效性校验"));
        return new CommonResponse<String>().ok("通过有效性校验");
    }


    /**
     * 判断是否满足审单条件
     *
     * @param orderMain 订单主信息
     * @return 订单主信息
     */
    private CommonResponse<OrderMain> conditionAuditOrder(OrderMain orderMain,
                                                          StrategyAudit auditStrategy, AuditParamDTO param) {

        //组装订单信息
        if (param.getInstall()) {
            OrderMain orderFullInfo = orderMainService.getOrderFullInfoById(orderMain.getOrderId());

            if (Objects.isNull(orderFullInfo)) {
                logger.error("订单[{}]信息主体不完整;", orderMain.getOrderId());
                return new CommonResponse<OrderMain>().error(Constants.FAIL, "订单信息主体不完整;");
            }

            BeanUtils.copyProperties(orderFullInfo, orderMain);
        }

        if (Objects.isNull(orderMain.getOrderStatusinfo()) || Constants.INVALID.equals(orderMain.getOrderStatusinfo().getStatus())) {
            logger.error("订单[{}]状态无效,不能审核订单;", orderMain.getOrderId());
            return new CommonResponse<OrderMain>().error(Constants.FAIL, "订单状态无效,不能审核订单;");
        }

        //订单流程状态
        if (orderMain.getOrderStatusinfo().getFlag() >= OrderFlagEnum.AUDIT.getCode()) {
            logger.error("订单[{}]已审核或已取消,不能审核订单;", orderMain.getOrderId());
            return new CommonResponse<OrderMain>().error(Constants.FAIL, "已审核或已取消,不能审核订单;");
        }

        //订单锁定
        if (Constants.YES.equals(orderMain.getOrderLock())) {
            //获取当前登陆人编码
            Long userId = ShiroUtils.getSysUser().getUserId();
            if (!userId.toString().equals(orderMain.getLockUser())) {
                logger.error("当前登录人[{}]不是订单锁定人[{}],不能审核该订单[{}];", userId,
                        orderMain.getLockUser(), orderMain.getOrderId());
                return new CommonResponse<OrderMain>().error(Constants.FAIL,
                        "当前登录人不是订单锁定人[" + orderMain.getLockUser() + "],不能审核订单;");
            }
        }

        //审单策略
        StrategyAudit strategyByShop = strategyAuditService.getFullInfoStrategyByShop(orderMain.getShop());

        if (Objects.isNull(strategyByShop)) {
            logger.error("订单[{}]店铺[{}]未设置审单策略,不能审核订单;", orderMain.getOrderId(), orderMain.getShop());
            return new CommonResponse<OrderMain>().error(Constants.FAIL,
                    "店铺" + orderMain.getShop() + "未设置审单策略,不能审核订单;");
        }

        BeanUtils.copyProperties(strategyByShop, auditStrategy);

        logger.debug("审核订单:满足审单条件,完成装载订单[{}]对象;", orderMain.getOrderId());

        return new CommonResponse<OrderMain>().ok(orderMain);
    }


    @Override
    public CommonResponse<String> afterAuditOrder(AuditOrderEvent event) {

        //是否通过审核拦截
        boolean access = true;

        //订单拦截信息
        List<OrderInterceptInfo> orderInterceptList = event.getOrderInterceptInfoList();

        OrderInterceptInfo interceptInfo = new OrderInterceptInfo();

        if (orderInterceptList.isEmpty()) {
            logger.debug("订单[{}]审核无拦截信息", event.getOrderMain().getOrderId());
        } else if (orderInterceptList.size() == 1) {

            access = false;

            logger.debug("订单[{}]存在一条拦截信息[{}]", event.getOrderMain().getOrderId(),
                    orderInterceptList.get(0).getContent());

            interceptInfo = orderInterceptList.get(0);
        } else {

            access = false;

            //记录拦截原因
            StringBuilder reason = new StringBuilder();

            for (OrderInterceptInfo orderInterceptInfo : orderInterceptList) {
                reason.append(orderInterceptInfo.getContent()).append(";");
            }

            logger.debug("订单[{}]存在多条拦截信息[{}]", event.getOrderMain().getOrderId(), reason.toString());

            //订单号
            interceptInfo.setOrderId(event.getOrderMain().getOrderId());
            //多条拦截信息
            interceptInfo.setType(OrderInterceptTypeEnum.MULTIPLE.getCode());
            //拦截原因
            interceptInfo.setContent(reason.toString());
        }

        if (access) {
            //更新订单状态
            boolean b = updateOrderFlagService.auditUpdateOrder(event.getOrderMain());
            if (b) {
                //清除拦截信息
                orderInterceptService.deleteByOrderId(event.getOrderMain().getOrderId());

                //耗时记录日志
                long spendTime = System.currentTimeMillis() - event.getParam().getStartTime();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("完成审核订单,耗时:").append(spendTime).append("ms");
                //保存轨迹
                traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                        OrderOperateType.AUDIT.getValue(), TraceLevelType.STATUS.getKey(), stringBuilder.toString()));

                logger.info("订单[{}][{}]", event.getOrderMain().getOrderId(), stringBuilder.toString());

                //完成审单流程,交由事件驱动判断下一步动作
                eventDrivenService.finishAudit(event.getOrderMain(), event.getParam());
            } else {
                //保存轨迹
                traceLogApi.addTraceLogAction(TraceLogFactory.create(OrderModuleConstants.ORDER_MAIN, event.getOrderMain().getOrderId(),
                        OrderOperateType.AUDIT.getValue(), TraceLevelType.ABNORMAL.getKey(), "更新订单状态失败,请检查日志"));

                logger.error("订单[{}]审核更新订单状态失败,method:orderMainService.auditUpdateOrder(orderMain)", event.getOrderMain().getOrderId());
            }
        } else {
            //审单未通过,记录拦截信息
            orderInterceptService.addOrUpdateOrderIntercept(interceptInfo.getOrderId(),
                    interceptInfo.getType(), interceptInfo.getContent());
        }

        return new CommonResponse<String>().ok(interceptInfo.getOrderId());
    }

}

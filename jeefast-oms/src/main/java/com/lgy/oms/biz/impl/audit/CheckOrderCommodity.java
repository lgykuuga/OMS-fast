package com.lgy.oms.biz.impl.audit;

import com.lgy.common.constant.Constants;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.constants.OrderOperateType;
import com.lgy.oms.constants.TraceLevelType;
import com.lgy.oms.disruptor.audit.sub.AuditOrderEvent;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.StrategyAuditCommodity;
import com.lgy.oms.domain.StrategyAuditSpecial;
import com.lgy.oms.domain.TraceLog;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderInterceptInfo;
import com.lgy.oms.enums.order.OrderInterceptTypeEnum;
import com.lgy.oms.enums.strategy.AuditCommodityEnum;
import com.lgy.oms.enums.strategy.AuditSpecialEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 校验订单明细
 * @Author LGy
 * @Date 2020/1/8 18:00
 **/
@Service
public class CheckOrderCommodity {

    private static Logger logger = LoggerFactory.getLogger(CheckOrderCommodity.class);

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    public void execute(AuditOrderEvent event) {

        StrategyAudit auditStrategy = event.getAuditStrategy();

        //开启特定信息拦截
        if (Constants.ON.equals(auditStrategy.getOrderIntercept().toString())) {
            checkOrderIntercept(event, auditStrategy);
        } else {
            logger.debug("订单[{}]店铺策略[{}]未开启特定信息拦截,不校验规则",
                    event.getOrderMain().getOrderId(), auditStrategy.getGco());
        }

        if (Constants.ON.equals(auditStrategy.getCommodityIntercept().toString())) {
            //开始校验指定商品拦截
            commodityIntercept(event, auditStrategy);
        } else {
            logger.debug("订单[{}]店铺策略[{}]未开启指定商品拦截,不校验规则",
                    event.getOrderMain().getOrderId(), auditStrategy.getGco());
        }
    }

    private void checkOrderIntercept(AuditOrderEvent event, StrategyAudit strategy) {
        //订单特定信息拦截设置
        if (strategy.getAuditSpecials() == null || strategy.getAuditSpecials().isEmpty()) {
            return;
        }

        //拦截信息
        StringBuilder stringBuffer = new StringBuilder();

        for (StrategyAuditSpecial auditSpecial : strategy.getAuditSpecials()) {
            //可设置多条用,分隔
            String[] values = auditSpecial.getValue().split(Constants.COMMA);

            if (AuditSpecialEnum.BUYER_ID.getCode().equals(auditSpecial.getType())) {
                //买家ID
                for (String value : values) {
                    if (value.equals(event.getOrderMain().getOrderBuyerinfo().getBuyerId())) {
                        stringBuffer.append("策略拦截买家ID:").append(value);
                    }
                }
            } else if (AuditSpecialEnum.BUYER_NAME.getCode().equals(auditSpecial.getType())) {
                //买家姓名
                for (String value : values) {
                    if (value.equals(event.getOrderMain().getOrderBuyerinfo().getBuyerName())) {
                        stringBuffer.append("策略拦截买家姓名:").append(value);
                    }
                }
            } else if (AuditSpecialEnum.BUYER_PHONE.getCode().equals(auditSpecial.getType())) {
                //买家电话
                for (String value : values) {
                    if (value.equals(event.getOrderMain().getOrderBuyerinfo().getBuyerPhone())
                            || value.equals(event.getOrderMain().getOrderBuyerinfo().getConsigneeMobile())) {
                        stringBuffer.append("策略拦截买家电话:").append(value);
                    }
                }
            } else if (AuditSpecialEnum.ADDRESS.getCode().equals(auditSpecial.getType())) {
                //tips:校验特殊地址前要把特定信息拦截设置set on
                if (Constants.ON.equals(strategy.getAddressIntercept().toString())) {
                    //地址拼接 国家 + 省份 + 城市 + 区域
                    StringBuilder address = new StringBuilder();
                    address.append(event.getOrderMain().getOrderBuyerinfo().getNation())
                            .append(event.getOrderMain().getOrderBuyerinfo().getProvince())
                            .append(event.getOrderMain().getOrderBuyerinfo().getCity())
                            .append(event.getOrderMain().getOrderBuyerinfo().getDistrict());
                    //地址
                    for (String value : values) {
                        if (address.toString().contains(value)) {
                            stringBuffer.append("策略拦截地址:").append(value);
                        }
                    }
                }

            }
        }

        if (StringUtils.isNotEmpty(stringBuffer.toString())) {
            logger.debug("订单[{}]拦截信息[{}]", event.getOrderMain().getOrderId(), stringBuffer.toString());

            //非强制审核,则拦截
            if (!event.getParam().getEnforce()) {
                List<OrderInterceptInfo> orderInterceptInfoList = event.getOrderInterceptInfoList();
                //将订单拦截信息放入event
                OrderInterceptInfo interceptInfo = new OrderInterceptInfo();
                interceptInfo.setOrderId(event.getOrderMain().getOrderId());
                //特定信息拦截
                interceptInfo.setType(OrderInterceptTypeEnum.SPECIAL_INFO.getCode());
                interceptInfo.setContent(stringBuffer.toString());
                orderInterceptInfoList.add(interceptInfo);
            }

            //保存轨迹服务
            traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDERMAIN, event.getOrderMain().getOrderId(),
                    OrderOperateType.AUDIT_SPECIAL_INTERCEPT.getValue(), TraceLevelType.TRACE.getKey(), stringBuffer.toString()));
        }


    }

    private void commodityIntercept(AuditOrderEvent event, StrategyAudit strategy) {

        //订单商品拦截设置
        if (strategy.getAuditCommodities() == null || strategy.getAuditCommodities().isEmpty()) {
            return;
        }

        //订单明细信息
        List<OrderDetail> orderDetails = event.getOrderMain().getOrderDetails();

        //拦截信息
        StringBuilder intercept = new StringBuilder();

        for (StrategyAuditCommodity auditCommodity : strategy.getAuditCommodities()) {

            //可设置多条用,分隔
            String[] values = auditCommodity.getValue().split(Constants.COMMA);

            if (AuditCommodityEnum.COMMODITY.getCode().equals(auditCommodity.getType())) {
                //商品编码
                for (String value : values) {
                    for (OrderDetail orderDetail : orderDetails) {
                        if (value.equals(orderDetail.getCommodity())) {
                            intercept.append("策略拦截商品编码:").append(orderDetail.getCommodity());
                        }
                    }
                }
            } else if (AuditCommodityEnum.TITLE.getCode().equals(auditCommodity.getType())) {
                //商品名称
                for (String value : values) {
                    for (OrderDetail orderDetail : orderDetails) {
                        if (orderDetail.getTitle().contains(value)) {
                            intercept.append("策略拦截商品名称:").append(orderDetail.getCommodity());
                        }
                    }
                }
            } else if (AuditCommodityEnum.BRAND.getCode().equals(auditCommodity.getType())) {
                //商品品牌
                for (String value : values) {
                    for (OrderDetail orderDetail : orderDetails) {
                        if (value.equals(orderDetail.getBrand())) {
                            intercept.append("策略拦截商品品牌:").append(orderDetail.getCommodity());
                        }
                    }
                }
            } else if (AuditCommodityEnum.CATEGORY.getCode().equals(auditCommodity.getType())) {
                //商品品牌
                for (String value : values) {
                    for (OrderDetail orderDetail : orderDetails) {
                        if (value.equals(orderDetail.getCategory())) {
                            intercept.append("策略拦截商品类别:").append(orderDetail.getCommodity());
                        }
                    }
                }
            }

            if (StringUtils.isNotEmpty(intercept.toString())) {
                logger.debug("订单[{}]拦截商品信息[{}]", event.getOrderMain().getOrderId(),
                        intercept.toString());

                //非强制审核,则拦截
                if (!event.getParam().getEnforce()) {
                    List<OrderInterceptInfo> orderInterceptInfoList = event.getOrderInterceptInfoList();
                    //将订单拦截信息放入event
                    OrderInterceptInfo interceptInfo = new OrderInterceptInfo();
                    interceptInfo.setOrderId(event.getOrderMain().getOrderId());
                    //指定商品拦截
                    interceptInfo.setType(OrderInterceptTypeEnum.SPECIAL_COMMODITY.getCode());
                    interceptInfo.setContent(intercept.toString());
                    orderInterceptInfoList.add(interceptInfo);
                }

                //保存轨迹服务
                traceLogApi.addTraceLogAction(new TraceLog(OrderModuleConstants.ORDERMAIN, event.getOrderMain().getOrderId(),
                        OrderOperateType.AUDIT_DETAIL_INTERCEPT.getValue(), TraceLevelType.TRACE.getKey(), intercept.toString()));
            }
        }
    }


}

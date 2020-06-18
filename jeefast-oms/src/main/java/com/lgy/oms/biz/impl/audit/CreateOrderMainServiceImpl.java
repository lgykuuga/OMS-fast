package com.lgy.oms.biz.impl.audit;


import com.lgy.base.service.ICommodityService;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.common.utils.StringUtils;
import com.lgy.oms.biz.ICreateOrderMainService;
import com.lgy.oms.biz.IOrderDetailProcessingService;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.service.*;
import com.lgy.system.incrementer.IDIncrementer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @Description 创建主订单信息实现
 * @Author LGy
 * @Date 2019/12/10
 */
@Service
public class CreateOrderMainServiceImpl implements ICreateOrderMainService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发号器
     */
    @Autowired
    IDIncrementer idIncrementer;
    /**
     * 商品档案Service
     */
    @Autowired
    ICommodityService commodityService;
    /**
     * 订单明细处理Service
     */
    @Autowired
    IOrderDetailProcessingService orderDetailProcessingService;

    /**
     * 订单主信息Service
     */
    @Autowired
    IOrderMainService orderMainService;
    /**
     * 订单买家信息Service
     */
    @Autowired
    IOrderBuyerInfoService orderBuyerInfoService;
    /**
     * 订单拦截信息Service
     */
    @Autowired
    IOrderInterceptService orderInterceptService;
    /**
     * 订单支付信息Service
     */
    @Autowired
    IOrderPayInfoService orderPayInfoService;
    /**
     * 订单状态信息Service
     */
    @Autowired
    IOrderStatusService orderStatusService;
    /**
     * 订单类型信息Service
     */
    @Autowired
    IOrderTypeInfoService orderTypeInfoService;
    /**
     * 订单明细信息Service
     */
    @Autowired
    IOrderDetailService orderDetailService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResponse<OrderMain> saveOrder(OrderMain orderMain) {

        boolean flag = true;

        if (StringUtils.isEmpty(orderMain.getOrderId())) {
            //若不存在单号,则生成单据流水号
            String orderId = idIncrementer.getNextId(OrderModuleConstants.ORDER_MAIN);
            orderMain.setOrderId(orderId);
        }

        try {
            //订单买家信息
            if (orderMain.getOrderBuyerinfo() != null) {
                orderMain.getOrderBuyerinfo().setOrderId(orderMain.getOrderId());
                orderBuyerInfoService.save(orderMain.getOrderBuyerinfo());
            }
            //订单支付信息
            if (orderMain.getOrderPayinfo() != null) {
                orderMain.getOrderPayinfo().setOrderId(orderMain.getOrderId());
                orderPayInfoService.save(orderMain.getOrderPayinfo());
            }
            //订单业务类型信息
            if (orderMain.getOrderTypeinfo() != null) {
                orderMain.getOrderTypeinfo().setOrderId(orderMain.getOrderId());
                orderTypeInfoService.save(orderMain.getOrderTypeinfo());
            }
            //订单状态类型信息
            if (orderMain.getOrderStatusinfo() != null) {
                orderMain.getOrderStatusinfo().setOrderId(orderMain.getOrderId());
                orderStatusService.save(orderMain.getOrderStatusinfo());
            }
            //订单异常类型信息
            if (orderMain.getOrderInterceptInfo() != null) {
                orderMain.getOrderInterceptInfo().setOrderId(orderMain.getOrderId());
                orderInterceptService.save(orderMain.getOrderInterceptInfo());
            }
            //订单明细信息
            if (StringUtils.isNotEmpty(orderMain.getOrderDetails())) {
                for (OrderDetail orderDetail : orderMain.getOrderDetails()) {
                    orderDetail.setOrderId(orderMain.getOrderId());
                }
                orderDetailService.saveBatch(orderMain.getOrderDetails());
            }

            orderMainService.save(orderMain);
        } catch (Exception e) {
            flag = false;
            logger.error("保存单据" + orderMain.getSourceId() + "失败。", e);
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        if (flag) {
            return new CommonResponse<OrderMain>().ok(orderMain);
        }

        return new CommonResponse<OrderMain>().error(Constants.FAIL, "保存订单失败,请检查日志");
    }

}

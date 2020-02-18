package com.lgy.oms.biz.impl.common;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.ICancelOrderService;
import com.lgy.oms.biz.IUpdateOrderFlagService;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.domain.order.OrderStatusInfo;
import com.lgy.oms.enums.distribution.DistributionSourceTypeEnum;
import com.lgy.oms.enums.order.OrderFlagEnum;
import com.lgy.oms.interfaces.common.dto.OrderDTO;
import com.lgy.oms.mapper.OrderMainMapper;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IOrderStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @Description 取消订单服务实现
 * @Author LGy
 * @Date 2019/10/15
 */
@Service
public class UpdateOrderFlagServiceImpl implements IUpdateOrderFlagService {

    private static Logger logger = LoggerFactory.getLogger(UpdateOrderFlagServiceImpl.class);

    @Autowired
    IOrderMainService orderMainService;
    /**
     * 订单状态信息
     */
    @Autowired
    IOrderStatusService orderStatusService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditUpdateOrder(OrderMain orderMain) {

        boolean flag;

        OrderStatusInfo orderStatusinfo = orderMain.getOrderStatusinfo();
        //更新状态为已审核
        orderStatusinfo.setFlag(OrderFlagEnum.AUDIT.getCode());
        //更新条件
        UpdateWrapper<OrderStatusInfo> orderStatusWrapper = new UpdateWrapper<>();
        orderStatusWrapper.eq("order_id", orderMain.getOrderId());
        orderStatusWrapper.eq("status", Constants.VALID);
        orderStatusWrapper.in("flag", OrderFlagEnum.NEW.getCode(), OrderFlagEnum.EDIT.getCode());

        flag = orderStatusService.update(orderMain.getOrderStatusinfo(), orderStatusWrapper);

        if (flag) {
            //设置拦截放行
            orderMain.setIntercept(Constants.NO);
            //设置解冻
            orderMain.setFrozen(Constants.NO);

            UpdateWrapper<OrderMain> mainUpdateWrapper = new UpdateWrapper<>();
            mainUpdateWrapper.eq("order_id", orderMain.getOrderId());
            flag = orderMainService.update(orderMain, mainUpdateWrapper);
        }

        if (!flag) {
            //更新失败,手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean distributionUpdateOrder(DistributionOrder distributionOrder) {

        boolean flag = true;
        //订单编号
        String orderId = distributionOrder.getOrderId();

        OrderStatusInfo orderStatusInfo = new OrderStatusInfo();

        //1.判断部分配货还是完全配货
        if (DistributionSourceTypeEnum.NORMAL.getCode().equals(distributionOrder.getSourceType())) {
            //来源类型为正常订单,未经过拆分,设置订单为已配货状态
            orderStatusInfo.setFlag(OrderFlagEnum.DISTRIBUTION.getCode());
        } else if (DistributionSourceTypeEnum.SPLIT.getCode().equals(distributionOrder.getSourceType())) {
            //来源类型为拆分,需要判断本次配货是部分配货还是已全部配货
            orderStatusInfo.setFlag(OrderFlagEnum.DISTRIBUTION.getCode());

            OrderMain order = orderMainService.getOrderById(orderId);

            //配货单明细数量
            Integer distributionOrderQty = distributionOrder.getQty();
            //订单明细数量
            Integer orderQty = order.getQty();
            //订单已配货数量
            Integer distributionQty = order.getDistributionQty();

            //订单已配货数量+本次配货数量
            int qty = distributionOrderQty + distributionQty;

            if (qty == orderQty) {
                //配货单明细数量 + 订单已配货数量 = 订单明细数量,说明本次配货单是最后一个配货成功的订单,修改订单为完全配货状态
                orderStatusInfo.setFlag(OrderFlagEnum.DISTRIBUTION.getCode());
            } else if (qty < orderQty) {
                //小于则说明是部分配货
                orderStatusInfo.setFlag(OrderFlagEnum.PART_DISTRIBUTION.getCode());
            } else {
                logger.error("订单已配货数量+本次配货数量>订单明细数量,代码错误");
                flag = false;
            }
        }

        if (flag) {
            UpdateWrapper<OrderStatusInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("order_id", orderId);
            flag = orderStatusService.update(orderStatusInfo, updateWrapper);
        }

        return flag;
    }
}

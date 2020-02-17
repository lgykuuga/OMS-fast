package com.lgy.oms.biz.impl.common;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.common.constant.Constants;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.ICancelOrderService;
import com.lgy.oms.biz.IUpdateOrderFlagService;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.domain.order.OrderStatusInfo;
import com.lgy.oms.enums.order.OrderFlagEnum;
import com.lgy.oms.interfaces.common.dto.OrderDTO;
import com.lgy.oms.mapper.OrderMainMapper;
import com.lgy.oms.service.IOrderMainService;
import com.lgy.oms.service.IOrderStatusService;
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

        boolean flag = false;

        OrderStatusInfo orderStatusinfo = orderMain.getOrderStatusinfo();
        //更新状态为已审核
        orderStatusinfo.setFlag(OrderFlagEnum.AUDIT.getCode());
        //更新条件
        UpdateWrapper<OrderStatusInfo> orderStatusWrapper = new UpdateWrapper<>();
        orderStatusWrapper.eq("order_id", orderMain.getOrderId());
        orderStatusWrapper.eq("status", Constants.VALID);
        orderStatusWrapper.in("flag", OrderFlagEnum.NEW.getCode(), OrderFlagEnum.EDIT.getCode());

        boolean update = orderStatusService.update(orderMain.getOrderStatusinfo(), orderStatusWrapper);

        if (update) {
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
    public boolean distributionUpdateOrder(OrderMain orderMain) {
        boolean flag = false;

        OrderStatusInfo orderStatusinfo = orderMain.getOrderStatusinfo();
        //更新状态为已审核
        orderStatusinfo.setFlag(OrderFlagEnum.AUDIT.getCode());
        //更新条件
        UpdateWrapper<OrderStatusInfo> orderStatusWrapper = new UpdateWrapper<>();
        orderStatusWrapper.eq("order_id", orderMain.getOrderId());
        orderStatusWrapper.eq("status", Constants.VALID);
        orderStatusWrapper.in("flag", OrderFlagEnum.NEW.getCode(), OrderFlagEnum.EDIT.getCode());

        boolean update = orderStatusService.update(orderMain.getOrderStatusinfo(), orderStatusWrapper);

        if (update) {
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
}

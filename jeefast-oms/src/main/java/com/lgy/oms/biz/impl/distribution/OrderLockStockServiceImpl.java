package com.lgy.oms.biz.impl.distribution;


import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.IOrderLockStockService;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.distribution.DistributionOrder;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderLockStockEnum;
import com.lgy.oms.enums.strategy.DistributionLockModelEnum;
import com.lgy.oms.service.IDistributionOrderService;
import com.lgy.oms.service.IStockLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 订单锁定(占用)库存实现
 * @Author LGy
 * @Date 2020/2/21
 */
@Service
public class OrderLockStockServiceImpl implements IOrderLockStockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDistributionOrderService distributionOrderService;

    /**
     * 订单轨迹信息
     */
    @Autowired
    TraceLogApi traceLogApi;

    /**
     * 库存锁定Service
     */
    @Autowired
    IStockLockService stockLockService;


    @Override
    public CommonResponse<String> execute(OrderMain orderMain, StrategyDistribution strategyDistribution,
                                                     DistributionParamDTO param, List<String> warehouseList) {


        if (OrderLockStockEnum.COMPLETE_LOCK.getCode().equals(orderMain.getLockStock())) {
            //订单完全占用
            return new CommonResponse<String>().ok("订单已完全占用,订单明细无需再次锁定库存");
        }

        //锁库成功标识
        boolean lock = false;
        //订单占用库存状态
        int lockStockStatus = OrderLockStockEnum.NONE.getCode();

        if (DistributionLockModelEnum.FORCE.getCode().equals(strategyDistribution.getLockModel()) ||
                !param.getCheckStock()) {
            //配货策略强制锁库或配货请求参数不校验库存
            //直接锁定库存.
            boolean lockStock = stockLockService.lockStock(orderMain, warehouseList.get(0));
            if (lockStock) {
                lock = true;
                //完全占用
                lockStockStatus = OrderLockStockEnum.COMPLETE_LOCK.getCode();
            }
        } else {
            //校验库存
            List<OrderDetail> orderDetails = orderMain.getOrderDetails();

            for (String warehouse : warehouseList) {
                for (OrderDetail orderDetail : orderDetails) {



                }
            }





        }


        if (lock) {
            orderMain.setLockStock(lockStockStatus);
        }

        return null;
    }
}

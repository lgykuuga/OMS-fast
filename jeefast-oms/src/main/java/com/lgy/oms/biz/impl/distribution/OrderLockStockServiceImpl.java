package com.lgy.oms.biz.impl.distribution;


import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.biz.ICheckStockService;
import com.lgy.oms.biz.IOrderLockStockService;
import com.lgy.oms.disruptor.tracelog.TraceLogApi;
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.dto.DistributionParamDTO;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.enums.order.OrderLackStockEnum;
import com.lgy.oms.enums.order.OrderLockStockEnum;
import com.lgy.oms.enums.strategy.DistributionLockModelEnum;
import com.lgy.oms.service.IDistributionOrderService;
import com.lgy.oms.service.IStockLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
     * 校验库存Service
     */
    @Autowired
    IStockLockService stockLockService;

    /**
     * 校验库存Service
     */
    @Autowired
    ICheckStockService checkStockService;


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
        //订单缺货状态
        int lackStockStatus = OrderLackStockEnum.NONE.getCode();


        if (DistributionLockModelEnum.FORCE.getCode().equals(strategyDistribution.getLockModel())
                || !param.getCheckStock()) {
            //配货策略强制锁库或配货请求参数不校验库存
            //直接锁定库存.
            boolean lockStock = stockLockService.lockStock(orderMain, warehouseList.get(0));
            if (lockStock) {
                lock = true;
                //完全占用
                lockStockStatus = OrderLockStockEnum.COMPLETE_LOCK.getCode();
            }
        } else {
            //开始校验库存

            //转换成库存锁定对象,便于统计(杜绝一个订单出现两条相同明细,从而造成统计可用库存数量错误)
            List<StockLock> stockLockList = stockLockService.orderConvert(orderMain.getOrderDetails());

            for (String warehouse : warehouseList) {

                //是否部分库存满足
                boolean isPartEnough = false;

                //是否所有明细满足库存
                boolean isEnough = true;

                for (StockLock stockLock : stockLockList) {
                    //获取订单明细商品可用库存数量
                    int availableStockQty = checkStockService.getAvailableStockQty(stockLock.getCommodity(), warehouse, stockLock.getOwner());

                    if (availableStockQty <= BigDecimal.ZERO.intValue()) {
                        logger.debug("订单[{}]货主[{}]明细商品[{}]仓库[{}]库存数量[{}]不足。", stockLock.getOrderId(),
                                stockLock.getOwner(), stockLock.getCommodity(), warehouse, availableStockQty);

                        isEnough = false;

                    } else {
                        //可用库存 >= 订单所需数量
                        if (availableStockQty >= stockLock.getQty())  {
                            isPartEnough = true;
                        } else {
                            logger.debug("订单[{}]货主[{}]明细商品[{}]仓库[{}]库存数量[{}]不足订单所需数量[{}]。", stockLock.getOrderId(),
                                    stockLock.getOwner(), stockLock.getCommodity(), warehouse, availableStockQty, stockLock.getQty());
                            isEnough = false;
                        }
                    }
                }

                if (isEnough) {
                    //所有明细满足库存



                    break;
                } else {
                    //判断全部缺货/部分缺货
                    if (isPartEnough) {
                        //部分库存满足
                        lackStockStatus = OrderLackStockEnum.PART_LACK.getCode();
                    } else {
                        //全部缺货
                        lackStockStatus = OrderLackStockEnum.COMPLETE_LACK.getCode();
                    }
                }







            }


        }


        if (lock) {
            orderMain.setLockStock(lockStockStatus);
        }

        return null;
    }
}

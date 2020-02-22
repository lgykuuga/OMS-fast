package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StockLockMapper;
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.service.IStockLockService;

import java.util.ArrayList;
import java.util.List;

/**
 * 库存锁定 服务层实现
 *
 * @author lgy
 * @date 2019-10-21
 */
@Service
public class StockLockServiceImpl extends ServiceImpl<StockLockMapper, StockLock> implements IStockLockService {

    @Override
    public boolean lockStock(OrderMain orderMain, String warehouse) {

        //订单明细
        List<OrderDetail> orderDetails = orderMain.getOrderDetails();

        List<StockLock> stockLocks = new ArrayList<>(orderDetails.size());

        for (OrderDetail orderDetail : orderDetails) {
            StockLock stockLock = new StockLock();
            stockLock.setWarehouse(warehouse);
            stockLock.setOwner(orderMain.getOwner());
            stockLock.setOrderId(orderMain.getOrderId());
            stockLock.setModule(OrderModuleConstants.ORDER_MAIN);
            stockLock.setCommodity(orderDetail.getCommodity());
            stockLock.setQty(orderDetail.getQty());
            stockLock.setRowNumber(orderDetail.getRowNumber());
            stockLocks.add(stockLock);
        }

        return this.saveBatch(stockLocks);
    }
}
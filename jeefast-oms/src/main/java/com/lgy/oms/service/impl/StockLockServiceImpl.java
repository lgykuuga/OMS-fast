package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.constants.OrderModuleConstants;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StockLockMapper;
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.service.IStockLockService;

import java.util.*;

/**
 * 库存锁定 服务层实现
 *
 * @author lgy
 * @date 2019-10-21
 */
@Service
public class StockLockServiceImpl extends ServiceImpl<StockLockMapper, StockLock> implements IStockLockService {

    @Autowired
    StockLockMapper stockLockMapper;


    @Override
    public boolean lockStock(OrderMain orderMain, String warehouse) {

        //订单明细
        List<OrderDetail> orderDetails = orderMain.getOrderDetails();

        List<StockLock> stockLocks = new ArrayList<>(orderDetails.size());

        for (OrderDetail orderDetail : orderDetails) {
            StockLock stockLock = new StockLock();
            stockLock.setWarehouse(warehouse);
            stockLock.setOwner(orderDetail.getOwner());
            stockLock.setOrderId(orderMain.getOrderId());
            stockLock.setModule(OrderModuleConstants.ORDER_MAIN);
            stockLock.setCommodity(orderDetail.getCommodity());
            stockLock.setQty(orderDetail.getQty());
            stockLock.setRowNumber(orderDetail.getRowNumber());
            stockLocks.add(stockLock);
        }

        return this.saveBatch(stockLocks);
    }

    @Override
    public int getStockLockQty(String commodity, String warehouse, String owner) {
        return stockLockMapper.getStockLockQty(commodity, warehouse, owner);
    }

    @Override
    public List<StockLock> orderConvert(List<OrderDetail> orderDetails) {

        //key:商品编码,目的是为了杜绝一个订单出现两条相同明细,从而造成统计可用库存数量错误
        Map<String, StockLock> map = new HashMap<>(orderDetails.size());

        for (OrderDetail orderDetail : orderDetails) {

            StockLock originStockLock = map.get(orderDetail.getCommodity());

            if (originStockLock == null) {
                StockLock stockLock = new StockLock();
                //未确认仓库
                stockLock.setWarehouse("");
                stockLock.setOwner(orderDetail.getOwner());
                stockLock.setOrderId(orderDetail.getOrderId());
                stockLock.setModule(OrderModuleConstants.ORDER_MAIN);
                stockLock.setCommodity(orderDetail.getCommodity());
                stockLock.setQty(orderDetail.getQty());
                stockLock.setRowNumber(orderDetail.getRowNumber());
                map.put(originStockLock.getCommodity(), stockLock);
            } else {
                //商品行序号
                originStockLock.setRowNumber(originStockLock.getRowNumber() + "," + orderDetail.getRowNumber());
                //数量
                originStockLock.setQty(originStockLock.getQty() + orderDetail.getQty());
                map.put(originStockLock.getCommodity(), originStockLock);
            }
        }

        return (List<StockLock>) map.values();

    }
}
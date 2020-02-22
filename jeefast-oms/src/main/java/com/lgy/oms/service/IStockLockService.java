package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.domain.order.OrderMain;

/**
 * 库存锁定 服务层
 *
 * @author lgy
 * @date 2019-10-21
 */
public interface IStockLockService extends IService<StockLock> {

    /**
     * 根据订单信息锁定库存
     *
     * @param orderMain 订单信息
     * @param warehouse 仓库
     * @return
     */
    boolean lockStock(OrderMain orderMain, String warehouse);
}
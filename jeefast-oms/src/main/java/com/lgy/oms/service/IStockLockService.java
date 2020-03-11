package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;

import java.util.List;

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

    /**
     * 获取商品库存锁定数量
     *
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner     货主编码
     * @return 商品数量
     */
    int getStockLockQty(String commodity, String warehouse, String owner);

    /**
     * 订单明细转换成库存锁定对象
     * @param orderDetails 订单明细
     * @return
     */
    List<StockLock> orderConvert(List<OrderDetail> orderDetails);
}
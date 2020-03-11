package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.Stock;

/**
 * 库存信息 服务层
 *
 * @author lgy
 * @date 2019-10-18
 */
public interface IStockService extends IService<Stock> {

    /**
     * 获取商品库存数量
     *
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner     货主编码
     * @return 商品数量
     */
    int getStockQty(String commodity, String warehouse, String owner);

    /**
     * 获取商品可用库存数量
     *
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner     货主编码
     * @return 商品数量
     */
    int getAvailableStockQty(String commodity, String warehouse, String owner);
}
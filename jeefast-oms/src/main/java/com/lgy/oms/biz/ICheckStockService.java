package com.lgy.oms.biz;

/**
 * @Description 获取库存Service接口
 * @Author LGy
 * @Date 2020/1/7
 */
public interface ICheckStockService {

    /**
     * 获取商品库存数量
     *
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner     货主编码
     * @return 商品数量
     */
    int getStockQty(String commodity, String warehouse, String owner);
}

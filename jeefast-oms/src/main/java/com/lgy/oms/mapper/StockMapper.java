package com.lgy.oms.mapper;

import com.lgy.oms.domain.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库存信息 数据层
 *
 * @author lgy
 * @date 2019-10-18
 */
public interface StockMapper extends BaseMapper<Stock> {

    /**
     * 获取商品库存数量
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner 货主
     * @return
     */
    int getStockQty(@Param("commodity")String commodity,
                    @Param("warehouse")String warehouse, @Param("owner")String owner);

    /**
     * 获取商品可用库存数量
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner 货主
     * @return
     */
    int getAvailableStockQty(@Param("commodity")String commodity,
                             @Param("warehouse")String warehouse, @Param("owner")String owner);
}
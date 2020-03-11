package com.lgy.oms.mapper;

import com.lgy.oms.domain.StockLock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库存锁定 数据层
 *
 * @author lgy
 * @date 2019-10-21
 */
public interface StockLockMapper extends BaseMapper<StockLock> {

    /**
     * 获取商品库存锁定数量
     *
     * @param commodity 商品编码
     * @param warehouse 仓库编码
     * @param owner     货主编码
     * @return 商品数量
     */
    int getStockLockQty(@Param("commodity")String commodity,
                        @Param("warehouse")String warehouse, @Param("owner")String owner);
}
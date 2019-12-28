package com.lgy.oms.mapper;

import com.lgy.oms.domain.StockLock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存锁定 数据层
 *
 * @author lgy
 * @date 2019-10-21
 */
@Mapper
public interface StockLockMapper extends BaseMapper<StockLock> {

}
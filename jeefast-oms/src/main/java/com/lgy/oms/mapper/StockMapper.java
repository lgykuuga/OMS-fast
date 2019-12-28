package com.lgy.oms.mapper;

import com.lgy.oms.domain.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存信息 数据层
 *
 * @author lgy
 * @date 2019-10-18
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}
package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.oms.domain.Stock;
import com.lgy.oms.mapper.StockMapper;
import com.lgy.oms.service.IStockService;
import org.springframework.stereotype.Service;

/**
 * 库存信息 服务层实现
 *
 * @author lgy
 * @date 2019-10-18
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements IStockService {

}
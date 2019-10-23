package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.lgy.oms.mapper.StockLockMapper;
import com.lgy.oms.domain.StockLock;
import com.lgy.oms.service.IStockLockService;

/**
 * 库存锁定 服务层实现
 *
 * @author lgy
 * @date 2019-10-21
 */
@Service
public class StockLockServiceImpl extends ServiceImpl<StockLockMapper, StockLock> implements IStockLockService {

}
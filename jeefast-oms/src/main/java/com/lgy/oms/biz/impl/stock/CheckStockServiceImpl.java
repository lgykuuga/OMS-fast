package com.lgy.oms.biz.impl.stock;


import com.lgy.oms.biz.ICheckStockService;
import com.lgy.oms.service.IStockLockService;
import com.lgy.oms.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 获取库存Service实现
 * @Author LGy
 * @Date 2020/2/23
 */
@Service
public class CheckStockServiceImpl implements ICheckStockService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 库存Service
     */
    @Autowired
    IStockService stockService;

    /**
     * 库存锁定Service
     */
    @Autowired
    IStockLockService stockLockService;

    @Override
    public int getStockQty(String commodity, String warehouse, String owner) {
        return stockService.getStockQty(commodity, warehouse, owner);
    }

    @Override
    public int getStockLockQty(String commodity, String warehouse, String owner) {
        return stockLockService.getStockLockQty(commodity, warehouse, owner);
    }

    @Override
    public int getAvailableStockQty(String commodity, String warehouse, String owner) {
        return stockService.getAvailableStockQty(commodity, warehouse, owner);
    }


}

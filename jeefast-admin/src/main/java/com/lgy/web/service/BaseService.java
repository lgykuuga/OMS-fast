package com.lgy.web.service;

import com.lgy.base.domain.Logistics;
import com.lgy.base.domain.Platform;
import com.lgy.base.domain.Shop;
import com.lgy.base.domain.Warehouse;
import com.lgy.base.service.*;
import com.lgy.system.domain.vo.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础资料模块 html调用 thymeleaf 实现参数管理
 *
 * @author lgy
 */
@Service("base")
public class BaseService {

    @Autowired
    IShopService shopService;
    @Autowired
    IPlatformService platformService;
    @Autowired
    IOwnerService ownerService;
    @Autowired
    IWarehouseService warehouseService;
    @Autowired
    ILogisticsService logisticsService;

    /**
     * 下来框查询货主
     *
     * @return 货主列表
     */
    public List<Config> selectOwner() {
        return ownerService.selectOwner();
    }

    /**
     * 下来框查询店铺
     *
     * @return 店铺列表
     */
    public List<Shop> selectShop() {
        return shopService.selectShop();
    }

    /**
     * 下来框查询平台
     *
     * @return 平台列表
     */
    public List<Platform> selectPlatform() {
        return platformService.selectPlatform();
    }

    /**
     * 下来框查询仓库
     *
     * @return 仓库列表
     */
    public List<Warehouse> selectWarehouse() {
        return warehouseService.selectWarehouse();
    }

    /**
     * 下来框查询物流商
     *
     * @return 仓库列表
     */
    public List<Logistics> selectLogistics() {
        return logisticsService.selectLogistics();
    }
}

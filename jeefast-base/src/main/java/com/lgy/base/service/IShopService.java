package com.lgy.base.service;

import com.lgy.base.domain.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 店铺档案 服务层
 *
 * @author lgy
 * @date 2019-10-10
 */
public interface IShopService extends IService<Shop> {

    /**
     * 查询可用店铺
     * @return 可用店铺列表
     */
    List<Shop> selectShop();
}
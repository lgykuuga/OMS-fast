package com.lgy.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.base.domain.Shop;
import com.lgy.common.service.CommonService;

import java.util.List;

/**
 * 店铺档案 服务层
 *
 * @author lgy
 * @date 2019-10-10
 */
public interface IShopService extends IService<Shop>, CommonService<Shop> {

    /**
     * 查询可用店铺
     *
     * @return 可用店铺列表
     */
    List<Shop> selectShop();

    /**
     * 获取店铺列表
     * @param shops 店铺列表编码
     * @return
     */
    List<Shop> listByGcos(List<String> shops);
}
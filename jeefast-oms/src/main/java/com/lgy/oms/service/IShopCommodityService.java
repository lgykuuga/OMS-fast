package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.common.core.domain.CommonResponse;
import com.lgy.oms.domain.ShopCommodity;

import java.util.List;

/**
 * 铺货关系 服务层
 *
 * @author lgy
 * @date 2019-10-22
 */
public interface IShopCommodityService extends IService<ShopCommodity> {

    /**
     * 导入铺货关系
     *
     * @param shopCommodities 铺货关系list
     * @param updateSupport   是否更新已存在的数据
     * @param operName        操作人编码
     * @return
     */
    CommonResponse<String> importShopCommodity(List<ShopCommodity> shopCommodities, boolean updateSupport, String operName);

}
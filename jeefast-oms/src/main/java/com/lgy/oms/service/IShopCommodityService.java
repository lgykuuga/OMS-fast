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


    /**
     * 根据订单信息获取铺货关系
     *
     * @param shop       店铺编码
     * @param numIid     商品数字ID
     * @param skuId      平台skuID
     * @param outerIid   商家外部编码
     * @param outerSkuId 外部网店自己定义的Sku编号
     * @return
     */
    CommonResponse<ShopCommodity> getShopCommodityByOrder(String shop, String numIid, String skuId, String outerIid, String outerSkuId);
}
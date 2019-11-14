package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.StrategyConvertShop;

import java.util.List;

/**
 * 转单策略 服务层
 *
 * @author lgy
 * @date 2019-10-31
 */
public interface IStrategyConvertService extends IService<StrategyConvert> {

    /**
     * 获取策略店铺
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyConvertShop> getConvertShop(String gco);

    /**
     * 更改策略店铺是否开启自动
     *
     * @param id   关系ID
     * @param auto 是否开启自动
     * @return
     */
    boolean changeAuto(Long id, String auto);

    /**
     * 根据策略编码删除策略店铺关系
     *
     * @param gco 策略编码
     * @return
     */
    Integer deleteConvertShopByGco(String gco);

    /**
     * 根据ID删除策略店铺关系
     *
     * @param ids 多条Id
     * @return
     */
    Integer deleteConvertShopById(List<String> ids);

    /**
     * 获取未加入该策略的店铺
     *
     * @param shopCode 店铺编码
     * @param shopName 店铺名称
     * @param gco      策略编码
     * @param enforce  是否强制添加
     * @return
     */
    List<StrategyConvertShop> addLoadShop(String shopCode, String shopName, String gco, boolean enforce);

    /**
     * 保存策略店铺
     * @param strategyConvertShopList
     * @param enforce  是否强制添加
     * @return
     */
    Integer saveStrategyConvertShop(List<StrategyConvertShop> strategyConvertShopList, boolean enforce);
}
package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.StrategyDistribution;
import com.lgy.oms.domain.StrategyDistributionShop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配货策略适用店铺 数据层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface StrategyDistributionShopMapper extends BaseMapper<StrategyDistributionShop> {

    /**
     * 获取策略店铺
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionShop> getStrategyShop(String gco);

    /**
     * 更改策略店铺是否开启自动
     *
     * @param id   关系ID
     * @param auto 是否开启自动
     * @return
     */
    boolean changeAuto(@Param("id") Long id, @Param("auto") String auto);

    /**
     * 获取未加入该策略的店铺
     *
     * @param shopCode 店铺编码
     * @param shopName 店铺名称
     * @param gco      策略编码
     * @return
     */
    List<StrategyDistributionShop> getNotJoThisStrategyShop(@Param("shopCode") String shopCode, @Param("shopName") String shopName,
                                                            @Param("gco") String gco);

    /**
     * 根据店铺编码获取策略
     *
     * @param shop 店铺编码
     * @return
     */
    StrategyDistribution getStrategyByShop(@Param("shop") String shop);

}
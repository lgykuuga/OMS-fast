package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.StrategyAudit;
import com.lgy.oms.domain.StrategyAuditShop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审单策略适用店铺 数据层
 *
 * @author lgy
 * @date 2019-12-17
 */
public interface StrategyAuditShopMapper extends BaseMapper<StrategyAuditShop> {

    /**
     * 获取策略店铺
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyAuditShop> getStrategyShop(String gco);

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
    List<StrategyAuditShop> getNotJoThisStrategyShop(@Param("shopCode") String shopCode, @Param("shopName") String shopName,
                                                     @Param("gco") String gco);

    /**
     * 根据店铺编码获取策略
     *
     * @param shop 店铺编码
     * @return
     */
    StrategyAudit getStrategyByShop(@Param("shop") String shop);
}
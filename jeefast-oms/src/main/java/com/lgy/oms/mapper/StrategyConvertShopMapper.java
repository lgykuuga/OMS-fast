package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.StrategyConvert;
import com.lgy.oms.domain.StrategyConvertShop;

import java.util.List;

/**
 * 转单策略店铺 数据层
 *
 * @author lgy
 * @date 2019-10-31
 */
public interface StrategyConvertShopMapper extends BaseMapper<StrategyConvertShop> {

    /**
     * 获取策略店铺
     * @param gco 策略编码
     * @return
     */
    List<StrategyConvertShop> getConvertShop(String gco);
}
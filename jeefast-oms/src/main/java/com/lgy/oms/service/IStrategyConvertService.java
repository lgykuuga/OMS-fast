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
}
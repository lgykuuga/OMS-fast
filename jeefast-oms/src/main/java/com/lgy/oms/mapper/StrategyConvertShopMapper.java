package com.lgy.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.oms.domain.StrategyConvertShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 转单策略店铺 数据层
 *
 * @author lgy
 * @date 2019-10-31
 */
@Mapper
public interface StrategyConvertShopMapper extends BaseMapper<StrategyConvertShop> {

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
    boolean changeAuto(@Param("id")Long id, @Param("auto")String auto);
}
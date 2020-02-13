package com.lgy.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.StrategyDistributionWarehouseSpecial;
import com.lgy.oms.domain.order.OrderMain;

import java.util.List;

/**
 * 配货策略特定信息分仓 服务层
 *
 * @author lgy
 * @date 2020-02-01
 */
public interface IStrategyDistributionWarehouseSpecialService extends IService<StrategyDistributionWarehouseSpecial> {

    /**
     * 根据策略编码获取策略
     *
     * @param gco 策略编码
     * @return
     */
    List<StrategyDistributionWarehouseSpecial> getStrategyByGco(String gco);

    /**
     * 调整优先级顺序
     *
     * @param id id
     * @param i  序号
     * @return
     */
    boolean updatePrePriority(Long id, int i);

    /**
     * 更新字段值
     *
     * @param id
     * @param field
     * @param value
     * @return
     */
    boolean changeField(Long id, String field, int value);

    /**
     * 根据特定信息分仓,返回可用仓库列表
     *
     * @param warehouseList 可用仓库
     * @param orderMain     订单信息
     * @param gco           策略编码
     * @param warehouseRule 策略设置
     * @return 可用仓库
     */
    List<String> getSpecialWarehouse(List<String> warehouseList, OrderMain orderMain, String gco, StrategyDistributionWarehouseRule warehouseRule);
}
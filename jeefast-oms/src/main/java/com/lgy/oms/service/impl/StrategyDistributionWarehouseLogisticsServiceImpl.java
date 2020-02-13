package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.StringUtils;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseLogistics;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mapper.StrategyDistributionWarehouseLogisticsMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseLogisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配货策略分仓物流规则 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseLogisticsServiceImpl extends ServiceImpl<StrategyDistributionWarehouseLogisticsMapper, StrategyDistributionWarehouseLogistics> implements IStrategyDistributionWarehouseLogisticsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<StrategyDistributionWarehouseLogistics> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseLogistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseLogistics entity = new StrategyDistributionWarehouseLogistics();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseLogistics rule = new StrategyDistributionWarehouseLogistics();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

    @Override
    public List<String> getLogisticsWarehouse(List<String> warehouseList, OrderMain orderMain,
                                              String gco, StrategyDistributionWarehouseRule warehouseRule) {

        if (StringUtils.isEmpty(orderMain.getLogistics())) {
            return warehouseList;
        }

        List<StrategyDistributionWarehouseLogistics> logisticsList = getStrategyByGcoAndLogistics(gco, orderMain.getLogistics());
        // 当条件为空是，返回原仓库信息
        if (logisticsList == null || logisticsList.isEmpty()) {
            return warehouseList;
        }

        //排除物流不到达的仓库
        List<StrategyDistributionWarehouseLogistics> list = new ArrayList<>(logisticsList.size());
        for (StrategyDistributionWarehouseLogistics logistics : logisticsList) {
            if (Constants.NO.equals(logistics.getArrive())) {
                list.add(logistics);
                if (warehouseList.contains(logistics.getWarehouse())) {
                    logger.debug("订单[{}]根据配货策略[{}]分仓规则-物流仓库规则,物流商[{}]不到达仓库[{}],移除该仓库", orderMain.getOrderId(),
                            gco, orderMain.getLogistics(), logistics.getWarehouse());
                    warehouseList.remove(logistics.getWarehouse());
                }
            }
        }
        logisticsList = list;

        for (StrategyDistributionWarehouseLogistics logistics : logisticsList) {
            if (warehouseList.contains(logistics.getWarehouse())) {
                if (Constants.YES.equals(warehouseRule.getMust())) {
                    //必须规则
                    warehouseList.clear();
                    warehouseList.add(logistics.getWarehouse());
                    logger.debug("订单[{}]匹配仓库:物流分配仓库规则匹配仓库(必须规则)[{}]", orderMain.getOrderId(), warehouseList);
                    return warehouseList;
                } else {
                    //非必须规则,调整仓库优先级为最高
                    warehouseList.remove(logistics.getWarehouse());
                    warehouseList.add(0, logistics.getWarehouse());
                    logger.debug("订单[{}]匹配仓库:物流分配仓库规则匹配仓库(非必须规则):[{}]", orderMain.getOrderId(), warehouseList);
                    return warehouseList;
                }
            }
        }

        return warehouseList;
    }

    /**
     * 根据策略编码和订单物流获取匹配策略列表
     *
     * @param gco       策略编码
     * @param logistics 物流商编码
     * @return 策略列表
     */
    private List<StrategyDistributionWarehouseLogistics> getStrategyByGcoAndLogistics(String gco, String logistics) {
        QueryWrapper<StrategyDistributionWarehouseLogistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        if (logistics.contains(Constants.COMMA)) {
            //包含逗号,说明有多条
            queryWrapper.in("logistics", Arrays.asList(logistics.split(Constants.COMMA)));
        } else {
            queryWrapper.eq("logistics", logistics);
        }
        queryWrapper.orderByAsc("priority");
        return this.list(queryWrapper);

    }
}
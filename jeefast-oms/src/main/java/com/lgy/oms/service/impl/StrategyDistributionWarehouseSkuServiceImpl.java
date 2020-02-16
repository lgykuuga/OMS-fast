package com.lgy.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.common.constant.Constants;
import com.lgy.common.utils.reflect.ReflectUtils;
import com.lgy.oms.domain.StrategyDistributionWarehouseRule;
import com.lgy.oms.domain.StrategyDistributionWarehouseSku;
import com.lgy.oms.domain.StrategyDistributionWarehouseWeight;
import com.lgy.oms.domain.order.OrderDetail;
import com.lgy.oms.domain.order.OrderMain;
import com.lgy.oms.mapper.StrategyDistributionWarehouseSkuMapper;
import com.lgy.oms.service.IStrategyDistributionWarehouseSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 配货策略仓库SKU信息 服务层实现
 *
 * @author lgy
 * @date 2020-02-01
 */
@Service
public class StrategyDistributionWarehouseSkuServiceImpl extends ServiceImpl<StrategyDistributionWarehouseSkuMapper, StrategyDistributionWarehouseSku> implements IStrategyDistributionWarehouseSkuService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<StrategyDistributionWarehouseSku> getStrategyByGco(String gco) {
        QueryWrapper<StrategyDistributionWarehouseSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        return this.list(queryWrapper);
    }

    @Override
    public List<StrategyDistributionWarehouseSku> getAvailableStrategyByGcoAndWarehouse(String gco, List<String> warehouse) {
        QueryWrapper<StrategyDistributionWarehouseSku> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gco", gco);
        queryWrapper.in("warehouse", warehouse);
        queryWrapper.orderByAsc("priority");
        return this.list(queryWrapper);
    }

    @Override
    public boolean updatePrePriority(Long id, int i) {
        StrategyDistributionWarehouseSku entity = new StrategyDistributionWarehouseSku();
        entity.setId(id);
        entity.setPriority(i);
        return this.updateById(entity);
    }

    @Override
    public boolean changeField(Long id, String field, int value) {
        StrategyDistributionWarehouseSku rule = new StrategyDistributionWarehouseSku();
        rule.setId(id);
        ReflectUtils.setFieldValue(rule, field, value);
        return updateById(rule);
    }

    @Override
    public List<String> getSkuWarehouse(List<String> warehouseList, OrderMain orderMain, String gco,
                                        StrategyDistributionWarehouseRule warehouseRule) {

        List<StrategyDistributionWarehouseSku> skuList = getAvailableStrategyByGcoAndWarehouse(gco, warehouseList);
        // 当条件为空是，返回原仓库信息
        if (skuList == null || skuList.isEmpty()) {
            return warehouseList;
        }

        //订单命中的规则
        List<StrategyDistributionWarehouseSku> toArriveList = getToArriveList(skuList, orderMain);
        if (toArriveList == null || toArriveList.isEmpty()) {
            return warehouseList;
        }

        if (Constants.YES.equals(warehouseRule.getMust())) {
            String warehouse = toArriveList.get(0).getWarehouse();
            logger.debug("订单[{}]根据sku仓库必须规则获取到指定仓库:[{}]", orderMain.getOrderId(), warehouse);
            warehouseList.clear();
            warehouseList.add(warehouse);
        } else {
            int i = 0;
            for (StrategyDistributionWarehouseSku sku : toArriveList) {
                if (warehouseList.contains(sku.getWarehouse())) {
                    logger.debug("订单[{}]根据sku仓库规则获取到优先级[{}]的仓库:[{}]", orderMain.getOrderId(), i, sku.getWarehouse());
                    //将到达区域匹配到的仓库放在优先级最高位,优先判断库存
                    warehouseList.remove(sku.getWarehouse());
                    warehouseList.add(i, sku.getWarehouse());
                    i++;
                }
            }
        }

        return warehouseList;
    }

    private List<StrategyDistributionWarehouseSku> getToArriveList(List<StrategyDistributionWarehouseSku> skuList, OrderMain orderMain) {

        List<StrategyDistributionWarehouseSku> skus = new ArrayList<>();

        List<OrderDetail> orderDetails = orderMain.getOrderDetails();

            for (StrategyDistributionWarehouseSku sku : skuList) {
                for (OrderDetail orderDetail : orderDetails) {
                    if (sku.getSku().equals(orderDetail.getCommodity())) {
                        skus.add(sku);
                    }
                }
            }
        return skus;

    }

}